let chart = null;

const graph = document.getElementById("myGraph");

window.addEventListener("load", drawGraph);

document.getElementById("functionOption").addEventListener("change", drawGraph);
document.getElementById("a").addEventListener("input", drawGraph);
document.getElementById("b").addEventListener("input", drawGraph);
document.getElementById("fxInput").addEventListener("input", drawGraph);

function drawGraph(){

const mode = document.querySelector('input[name="mode"]:checked').value;

const a = parseFloat(document.getElementById("a").value);
const b = parseFloat(document.getElementById("b").value);

let expr;

if(mode==="collection"){
expr = document.getElementById("functionOption").value;
}else{
expr = document.getElementById("fxInput").value;
}

if(!expr) return;

let fn;

try{
fn = buildFunction(expr);
}catch{
return;
}

const data = generateData(fn,a,b,0.05);

createChart(data,expr);

}


function buildFunction(expr){

expr = expr.replace(/\^/g,"**");

return new Function(
"x",
`
const sin=Math.sin,cos=Math.cos,tan=Math.tan;
const log=Math.log,exp=Math.exp,sqrt=Math.sqrt;
const abs=Math.abs,atan=Math.atan;
return ${expr};
`
);

}


function generateData(fn,start,end,step){

const data=[];

for(let x=start;x<=end;x+=step){

let y;

try{
y = fn(x);
}catch{
y = null;
}

data.push({x:x,y:y});

}

return data;

}


function createChart(data,name){

if(chart) chart.destroy();

chart = new Chart(graph,{

type:'line',

data:{
datasets:[
{
label:"f(x) = "+name,
data:data,
borderColor:"blue",
borderWidth:2,
pointRadius:0
}
]
},

options:{
responsive:true,
maintainAspectRatio:false,
parsing:false,
scales:{
x:{type:'linear'}
}
}

});

}