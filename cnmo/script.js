let chart = null;
const graph = document.getElementById("myGraph");

console.log("test");
const executeBtn = document.querySelector('.execute');
executeBtn.addEventListener('click', () => {
  const interactiveOption =
  document.querySelector('input[name="mode"]:checked').value;

  // Preluăm limitele intervalului
  const limits = document.querySelectorAll('.graphLimit');
  const a = parseFloat(limits[0].value);
  const b = parseFloat(limits[1].value);
  let fn, dataPoints;
  let inputFunction;
  

  // Verificăm ce opțiune a fost selectată și generăm graficul corespunzător
  if (interactiveOption === "collection") {
    const select = document.getElementById('functionOption');
    inputFunction = select.value;
    fn = buildFunction(inputFunction);
  }else if (interactiveOption === "interactive") {
    inputFunction = document.querySelector('#fxInput').value;
    fn = buildFunction(inputFunction);
  }

    dataPoints = generateData(fn, a, b, 0.01);
    createChart(dataPoints, inputFunction);

    let roots = [];
    roots = roots.concat(aproximateRoots(fn));

    drawPoint(roots);

    populateResultsTable(roots,fn);

});


function buildFunction(expr) {
  
  expr = expr.replace(/\^/g, '**');

  return new Function(
    'x',
    `
      const sin=Math.sin, cos=Math.cos, tan=Math.tan;
      const log=Math.log, exp=Math.exp, sqrt=Math.sqrt;
      const abs=Math.abs, pow=Math.pow;
      return ${expr};
    `
  );
}



//din functie creem datele pentru grafic
function generateData(fn, start, end, step) {
  const data = [];
  for (let x = start; x <= end; x += step) {
    data.push({ x: x, y: fn(x) });
  }
  return data;
}

function createChart(dataPoints,functionName) {

  if (chart) {
    chart.destroy();
  }

  chart = new Chart(graph, {
    type: 'line',
    data: {
      datasets: [{
        label: "f(x) = " + functionName,
        data: dataPoints,
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 2,
        pointRadius: 0,
        pointHoverRadius: 4,
        fill: false
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      parsing: false,
      scales: {
        x: {
          type: 'linear', 
          position: 'bottom'
        }
      }
    }
  });
}




function aproximateRoots(fn){
  const method = document.getElementById('aproxMethod').value;
  console.log("Selected method: " + method);

  const searchRangeInputs = document.querySelectorAll('.searchRange');
  const searchRange = {
    min: parseFloat(searchRangeInputs[0].value),
    max: parseFloat(searchRangeInputs[1].value)
  };

  const precision = 10 ** (-1 * document.getElementById('precision').value);

  const findAllToggle = document.getElementById('searchType').checked;
  let roots = [];
  switch (method) {
    case "Bisectie":
      roots = metodaBisectiei(fn, searchRange.min, searchRange.max, precision, 100, findAllToggle);
      break;
    case "Secantelor":
      roots = metodaSecantelor(fn, searchRange.min, searchRange.max, precision, 100, findAllToggle);
      break;
    case "Coardelor":
      roots = metodaCoardelor(fn, searchRange.min, searchRange.max, precision, 100, findAllToggle);
      break;
    case "Newton-Raphson":
      roots = metodaNewtonHybrid(fn, searchRange.min, searchRange.max, precision, 100, findAllToggle);
      break;
  }
    console.log("Roots found:", roots);
    return roots;
}


function metodaBisectiei(fn, a, b, tol, maxIter, findAll) {
  const step = 0.1;
  const startTime = performance.now();
    let roots = [];
    let x0 = a;
    let x1 = x0 + step;

  while (x1 <= b) {
    
    if (fn(x0) * fn(x1) < 0) {
      let a = x0;
      let b = x1;
      let c;
      let iterations = 0;
    
      for (let i = 0; i < maxIter; i++) {
        c = (a + b) / 2;
        if (Math.abs(fn(c)) < tol) {
          iterations = i + 1;
          break;
        }
        if (fn(c) * fn(a) < 0){
          b = c;
        }else{
          a = c;
        }
      }
      if (!findAll){
        return {root: c, iterations: iterations, timestamp: performance.now() - startTime};
      }
      roots.push({root: c, iterations: iterations, timestamp: performance.now() - startTime});
    }
    x0 = x1;
    x1 += step;
  }
  return findAll? roots: null;
}


function metodaSecantelor(fn, x0, x1, tol, maxIter, findAll) {
  const step = 0.01;
  let roots = [];
  let startX = x0;
  let endX = x1;

  while (startX < endX) {
    const s0 = startX;
    const s1 = startX + step;
    const startTime = performance.now();
    if (fn(s0) * fn(s1) < 0) {
      let a = s0;
      let b = s1;
      let c = a;
      let iterations = 0;

      for (let i = 0; i < maxIter; i++) {
        const fA = fn(a), fB = fn(b);
        if (fB - fA === 0) break; // avoid division by zero
        c = b - fB * (b - a) / (fB - fA);
        iterations = i + 1;
        if (Math.abs(fn(c)) < tol) break;
        a = b;
        b = c;
      }

      const timestamp = performance.now() - startTime;
      if (!findAll) return {root: c, iterations, timestamp};
      roots.push({root: c, iterations, timestamp});
    }
    startX += step;
  }

  return findAll ? roots : null;
}

function metodaCoardelor(fn, a, b, tol, maxIter, findAll) {
  const step = 0.01;
  let roots = [];
  let x0 = a;
  let x1 = x0 + step;

  while (x1 <= b) {
    const startTime = performance.now();
    if (fn(x0) * fn(x1) < 0) {
      let c = x1;
      let iterations = 0;
      let fa = fn(x0), fb = fn(x1);

      for (let i = 0; i < maxIter; i++) {
        c = x1 - fb * (x1 - x0) / (fb - fa);
        iterations = i + 1;
        const fc = fn(c);
        if (Math.abs(fc) < tol) break;
        x0 = x1;
        fa = fb;
        x1 = c;
        fb = fn(x1);
      }

      const timestamp = performance.now() - startTime;
      if (!findAll) return {root: c, iterations, timestamp};
      roots.push({root: c, iterations, timestamp});
    }
    x0 = x1;
    x1 += step;
  }

  return findAll ? roots : null;
}

function metodaNewtonHybrid(fn, min, max, tol, maxIter, findAll) {
  const scanStep = 0.05; // for detecting sign changes
  let roots = [];

  function derivative(f, x) {
    const h = 1e-8;
    return (f(x + h) - f(x - h)) / (2 * h);
  }

  let x0 = min;
  let x1 = x0 + scanStep;

  while (x1 <= max) {
    if (fn(x0) * fn(x1) < 0) {
      // Root is bracketed in [a, b]
      let a = x0;
      let b = x1;
      let x = (a + b) / 2; // start Newton from midpoint
      let iterations = 0;
      const startTime = performance.now();

      for (let i = 0; i < maxIter; i++) {
        iterations++;

        const fVal = fn(x);
        if (Math.abs(fVal) < tol) break;

        const d = derivative(fn, x);

        let xNew;
        if (Math.abs(d) > 1e-10) {
          // Newton step
          xNew = x - fVal / d;
        } else {
          // fallback to bisection
          xNew = (a + b) / 2;
        }

        // If Newton jumps out, fallback to bisection
        if (xNew < a || xNew > b) {
          xNew = (a + b) / 2;
        }

        // Update bracket
        if (fn(a) * fn(xNew) < 0) {
          b = xNew;
        } else {
          a = xNew;
        }

        x = xNew;
      }

      const timestamp = performance.now() - startTime;

      // Deduplicate roots
      if (!roots.some(r => Math.abs(r.root - x) < tol)) {
        if (!findAll) {
          return { root: x, iterations, timestamp };
        }
        roots.push({ root: x, iterations, timestamp });
      }
    }

    x0 = x1;
    x1 += scanStep;
  }

  return findAll ? roots : null;
}


function drawPoint(roots) {
  chart.data.datasets.push({
    type: 'scatter',
    label: 'Root',
    data: roots.map(root => ({ x: root.root, y: 0 })),
    backgroundColor: 'green',
    borderColor: 'green',
    pointRadius: 6,
    pointHoverRadius: 8
  });

  chart.update();
}


function populateResultsTable(roots,fn) {
const table = document.querySelector('.table-box table');
const tbody = table.getElementsByTagName('tbody')[0]; // get the first tbody
console.log(tbody);
  // Clear previous results
  tbody.innerHTML = '';

  // Populate new results
  roots.forEach((root, index) => {
    const row = document.createElement('tr');
    const cellIndex = document.createElement('td');
    const cellRoot = document.createElement('td');
    const cellFunction = document.createElement('td');
    const cellIterations = document.createElement('td');
    const cellTime = document.createElement('td');
    cellRoot.textContent = root.root.toFixed(6);
    cellFunction.textContent = fn(root.root).toFixed(12);
    cellIterations.textContent = root.iterations;
    cellTime.textContent = root.timestamp + ' ms';
    row.appendChild(cellRoot);
    row.appendChild(cellFunction);
    row.appendChild(cellIterations);
    row.appendChild(cellTime);
    tbody.appendChild(row);
  });


}