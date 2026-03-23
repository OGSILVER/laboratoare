// ─── Charts ───────────────────────────────────────────────────────────────────
let chartApprox = null;
let chartError  = null;

// ─── Mode toggle ──────────────────────────────────────────────────────────────
document.querySelectorAll('input[name="mode"]').forEach(r => {
  r.addEventListener("change", () => {
    const interactive = r.value === "interactive" && r.checked;
    document.getElementById("functionOption").style.display = interactive ? "none" : "";
    document.getElementById("fxInput").style.display        = interactive ? ""     : "none";
  });
});

// ─── Compute button ───────────────────────────────────────────────────────────
document.getElementById("computeBtn").addEventListener("click", run);

// ─── Main ─────────────────────────────────────────────────────────────────────
function run() {
  const a = parseFloat(document.getElementById("a").value);
  const b = parseFloat(document.getElementById("b").value);
  const n = parseInt(document.getElementById("nNodes").value);
  const m = parseInt(document.getElementById("mPoints").value);
  const method   = document.getElementById("method").value;
  const nodeType = document.getElementById("nodeType").value;

  if (isNaN(a) || isNaN(b) || a >= b) return alert("Invalid interval [a, b].");
  if (isNaN(n) || n < 2)              return alert("n must be ≥ 2.");

  const mode = document.querySelector('input[name="mode"]:checked').value;
  const expr = mode === "collection"
    ? document.getElementById("functionOption").value
    : document.getElementById("fxInput").value;

  let fn;
  try { fn = buildFunction(expr); } catch { return alert("Invalid function expression."); }

  // 1. Generate interpolation nodes xi
  const xi = generateNodes(a, b, n, nodeType);

  // 2. Compute yi = f(xi)
  const yi = xi.map(x => fn(x));

  // 3. Generate evaluation points zv (m equidistant points)
  const zv = linspace(a, b, m);

  // 4. Compute approximation at each z
  let Pz;
  if      (method === "lagrange")  Pz = zv.map(z => lagrange(xi, yi, z));
  else if (method === "piecewise") Pz = zv.map(z => piecewiseLagrange(xi, yi, z));
  else                             Pz = cubicSplineEval(xi, yi, zv);

  // 5. True function values at zv
  const fz = zv.map(z => fn(z));

  // 6. Error
  const err = zv.map((_, i) => Math.abs(fz[i] - Pz[i]));

  // 7. Draw
  drawApprox(zv, fz, Pz, xi, yi);
  drawError(zv, err);
  showStats(err, n, method);
}

// ─── Function builder ─────────────────────────────────────────────────────────
function buildFunction(expr) {
  expr = expr.replace(/\^/g, "**");
  return new Function("x", `
    const sin=Math.sin, cos=Math.cos, tan=Math.tan,
          log=Math.log, exp=Math.exp, sqrt=Math.sqrt,
          abs=Math.abs, atan=Math.atan, pow=Math.pow, PI=Math.PI;
    return ${expr};
  `);
}

// ─── Node generation ──────────────────────────────────────────────────────────
function generateNodes(a, b, n, type) {
  if (type === "equidistant") {
    return Array.from({length: n+1}, (_, i) => a + i * (b - a) / n);
  }
  if (type === "chebyshev") {
    return Array.from({length: n+1}, (_, i) =>
      (a + b) / 2 + (b - a) / 2 * Math.cos((2*i + 1) * Math.PI / (2*(n+1)))
    ).sort((x, y) => x - y);
  }
  // random — sort so algorithms work correctly
  return Array.from({length: n+1}, () => a + Math.random() * (b - a)).sort((x, y) => x - y);
}

function linspace(a, b, m) {
  return Array.from({length: m}, (_, i) => a + i * (b - a) / (m - 1));
}

// ─── ALGORITHM 1: Global Lagrange ─────────────────────────────────────────────
function lagrange(xi, yi, z) {
  const n = xi.length;
  let result = 0;
  for (let i = 0; i < n; i++) {
    let Li = 1;
    for (let j = 0; j < n; j++) {
      if (j !== i) Li *= (z - xi[j]) / (xi[i] - xi[j]);
    }
    result += yi[i] * Li;
  }  return result;
}

// ─── ALGORITHM 2: Piecewise Lagrange ─────────────────────────────────────────
// Linear piecewise (degree 1) — one segment per pair of consecutive nodes
function piecewiseLagrange(xi, yi, z) {
  const n = xi.length - 1;
  // Find segment
  let seg = n - 1; // default: last segment
  for (let i = 0; i < n; i++) {
    if (z <= xi[i + 1]) { seg = i; break; }
  }
  // Linear Lagrange on [xi[seg], xi[seg+1]]
  const x0 = xi[seg],   y0 = yi[seg];
  const x1 = xi[seg+1], y1 = yi[seg+1];
  const L0 = (z - x1) / (x0 - x1);
  const L1 = (z - x0) / (x1 - x0);
  return y0 * L0 + y1 * L1;
}

// ─── ALGORITHM 3: Natural Cubic Spline ───────────────────────────────────────
// Returns evaluated values at all zv points
function cubicSplineEval(xi, yi, zv) {
  const n = xi.length - 1; // number of intervals
  const h = Array.from({length: n}, (_, i) => xi[i+1] - xi[i]);

  // Build tridiagonal system for M (second derivatives at nodes)
  // Natural spline: M[0] = M[n] = 0
  const size = n - 1;
  if (size <= 0) return zv.map(z => lagrange(xi, yi, z));

  // Diagonals
  const diag  = Array(size).fill(0);
  const upper = Array(size - 1).fill(0);
  const lower = Array(size - 1).fill(0);
  const rhs   = Array(size).fill(0);

  for (let i = 0; i < size; i++) {
    diag[i] = 2 * (h[i] + h[i+1]);
    if (i < size - 1) upper[i] = h[i+1];
    if (i > 0)        lower[i-1] = h[i];
    rhs[i] = 6 * ((yi[i+2] - yi[i+1]) / h[i+1] - (yi[i+1] - yi[i]) / h[i]);
  }

  // Solve tridiagonal (Thomas algorithm)
  const M_inner = thomasSolve(diag, upper, lower, rhs);

  // Full M array with boundary conditions
  const M = [0, ...M_inner, 0];

  // Evaluate spline at each z in zv
  return zv.map(z => {
    // Find segment
    let seg = n - 1;
    for (let i = 0; i < n; i++) {
      if (z <= xi[i+1]) { seg = i; break; }
    }
    const hi = h[seg];
    const t  = z - xi[seg];
    const a  = yi[seg];
    const b  = (yi[seg+1] - yi[seg]) / hi - hi * (2*M[seg] + M[seg+1]) / 6;
    const c  = M[seg] / 2;
    const d  = (M[seg+1] - M[seg]) / (6 * hi);
    return a + b*t + c*t*t + d*t*t*t;
  });
}

// Thomas algorithm — solves tridiagonal system Ax = rhs
function thomasSolve(diag, upper, lower, rhs) {
  const n = diag.length;
  const c = [...diag];
  const d = [...rhs];
  const x = Array(n).fill(0);

  // Forward sweep
  for (let i = 1; i < n; i++) {
    const m = lower[i-1] / c[i-1];
    c[i] -= m * upper[i-1];
    d[i] -= m * d[i-1];
  }

  // Back substitution
  x[n-1] = d[n-1] / c[n-1];
  for (let i = n-2; i >= 0; i--) {
    x[i] = (d[i] - upper[i] * x[i+1]) / c[i];
  }
  return x;
}

// ─── Charts ───────────────────────────────────────────────────────────────────
function drawApprox(zv, fz, Pz, xi, yi) {
  if (chartApprox) chartApprox.destroy();

  // Node scatter points
  const nodeData = xi.map((x, i) => ({ x, y: yi[i] }));

  chartApprox = new Chart(document.getElementById("graphApprox"), {
    type: "line",
    data: {
      datasets: [
        {
          label: "f(x) — true",
          data: zv.map((x, i) => ({ x, y: fz[i] })),
          borderColor: "#4ea1ff",
          borderWidth: 2,
          pointRadius: 0,
          tension: 0.2
        },
        {
          label: "P(x) — approximation",
          data: zv.map((x, i) => ({ x, y: Pz[i] })),
          borderColor: "#ff6b6b",
          borderWidth: 2,
          borderDash: [5, 3],
          pointRadius: 0,
          tension: 0.2
        },
        {
          label: "Nodes (xᵢ, f(xᵢ))",
          data: nodeData,
          type: "scatter",
          borderColor: "#ffd166",
          backgroundColor: "#ffd166",
          pointRadius: 5,
          showLine: false
        }
      ]
    },
    options: chartOptions("f(x) and P(x)")
  });
}

function drawError(zv, err) {
  if (chartError) chartError.destroy();

  chartError = new Chart(document.getElementById("graphError"), {
    type: "line",
    data: {
      datasets: [{
        label: "|f(x) − P(x)|",
        data: zv.map((x, i) => ({ x, y: err[i] })),
        borderColor: "#06d6a0",
        backgroundColor: "rgba(6,214,160,0.07)",
        borderWidth: 2,
        pointRadius: 0,
        fill: true
      }]
    },
    options: chartOptions("Error")
  });
}

function chartOptions(title) {
  return {
    responsive: true,
    maintainAspectRatio: false,
    parsing: false,
    animation: { duration: 400 },
    scales: {
      x: {
        type: "linear",
        ticks: { color: "#aaa", maxTicksLimit: 10 },
        grid: { color: "#2a2a2a" }
      },
      y: {
        ticks: { color: "#aaa" },
        grid: { color: "#2a2a2a" }
      }
    },
    plugins: {
      legend: { labels: { color: "#eaeaea", boxWidth: 20 } },
      tooltip: { mode: "index", intersect: false }
    }
  };
}

// ─── Stats ────────────────────────────────────────────────────────────────────
function showStats(err, n, method) {
  const maxErr  = Math.max(...err);
  const meanErr = err.reduce((s, e) => s + e, 0) / err.length;

  const methodNames = { lagrange: "Lagrange", piecewise: "Piecewise", spline: "Cubic Spline" };

  document.getElementById("statMax").textContent  = maxErr.toExponential(3);
  document.getElementById("statMean").textContent = meanErr.toExponential(3);
  document.getElementById("statN").textContent    = n;
  document.getElementById("statAlgo").textContent = methodNames[method];
  document.getElementById("statsRow").style.display = "flex";
}
