import { useState } from 'react'
import colorArr from './assets/colors.json'
import ColorCircles from './components/ColorCircles'
import Canvas from './components/Canvas'
import Library from './components/Library'
import './App.css'

function App() {
  const [selectedColor, setSelectedColor] = useState(colorArr[0])
  const canvasSize = 25;

  const [grid, setGrid] = useState(() => Array(canvasSize).fill(null).map(() => Array(canvasSize).fill("#121212")))
 

  
  return (
    <main className="app-shell">
      <section className="app-card">
        <h1 className="app-title">Pixel Paint</h1>
        <ColorCircles colorArr={colorArr} selectedColor={selectedColor} setSelectedColor={setSelectedColor} />
        <Canvas selectedColor={selectedColor} grid={grid} setGrid={setGrid} />
        <Library grid={grid} setGrid={setGrid} canvasSize={canvasSize}/>
      </section>
    </main>
    )
}

export default App
