import {useState} from 'react';
import './Library.css'


export default function Library({ grid, setGrid, canvasSize }) {
    const [savedGrids, setSavedGrids] = useState([]);



    return (
        <>
        <div className="library-controls">
          <div className="save-btn" onClick={() => setSavedGrids([...savedGrids, grid])}>Save</div>
          <div className="reset-btn" 
          onClick={() => setGrid(() => Array(canvasSize).fill(null).map(() => Array(canvasSize).fill("#121212")))
          }>Reset</div>
        </div>
        <div className="saved-drawings-text">saved drawings</div>

            <div className="saved-drawings">
              {savedGrids.map((singleGrid, index) => (
                <div key={index} className="saved-drawing">
                
                  <div className="mini-canvas">
                    {singleGrid.map((row, rowIndex) => (
                      <div key={rowIndex} className="mini-canvas-row">
                        {row.map((color, colIndex) => (
                          <div
                            key={colIndex}
                            className="mini-canvas-square"
                            style={{ backgroundColor: color }}
                          />
                        ))}
                      </div>
                    ))}
                  </div>
                
                  <div className="saved-drawing-buttons">
                    <div className="load-btn" onClick={() => setGrid(singleGrid)}>Load</div>
                    <div className="delete-btn" onClick={() => setSavedGrids(prev => prev.filter((_, i) => i !== index))}>Delete</div>
                  </div>
                
                </div>
              ))}
            </div>
        </>
        

    )
}