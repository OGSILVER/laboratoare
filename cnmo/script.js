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
    const inputFunction = select.value;
    fn = buildFunction(inputFunction);
  }else if (interactiveOption === "interactive") {
    const inputFunction = document.querySelector('#fxInput').value;
    fn = buildFunction(inputFunction);
  }

    dataPoints = generateData(fn, a, b, 0.01);
    createChart(dataPoints, inputFunction);

    let roots = [];
    roots = roots.concat(aproximateRoots(fn));

    drawPoint(roots);

    populateResultsTable(roots);

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
  }
    console.log("Roots found:", roots);
    return roots;
}


function metodaBisectiei(fn, a, b, tol, maxIter, findAll) {
  const step = 0.01;

    let roots = [];
    let x0 = a;
    let x1 = x0 + step;

  while (x1 <= b) {
    if (fn(x0) * fn(x1) < 0) {
      let a = x0;
      let b = x1;
      let c;
    
      for (let i = 0; i < maxIter; i++) {
        c = (a + b) / 2;
        if (Math.abs(fn(c)) < tol) {
          break;
        }
        if (fn(c) * fn(a) < 0){
          b = c;
        }else{
          a = c;
        }
      }
      if (!findAll){
        return c;
      }
      roots.push(c);
    }
    x0 = x1;
    x1 += step;
  }
  return findAll? roots: null;
}


function metodaSecantelor(fn, x0, x1, tol, maxIter, findAll) {

}



function metodaCoardelor(fn, x0, x1, tol, maxIter, findAll) {

}



function drawPoint(roots) {
  chart.data.datasets.push({
    type: 'scatter',
    label: 'Root',
    data: roots.map(root => ({ x: root, y: 0 })),
    backgroundColor: 'green',
    borderColor: 'green',
    pointRadius: 6,
    pointHoverRadius: 8
  });

  chart.update();
}


function populateResultsTable(roots) {
  
}