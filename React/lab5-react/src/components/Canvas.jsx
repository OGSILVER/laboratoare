import {useState} from 'react';
import './Canvas.css'


export default function Canvas({ selectedColor, grid, setGrid }) {
    const [holding, setHolding] = useState(false);

    const paintCell = (rowIndex, colIndex, color) => {
        setGrid(prev => {
        const nextGrid = prev.map(row => [...row]);
        nextGrid[rowIndex][colIndex] = color.color;
        return nextGrid;
        });
    }


    return (
        <div
        onMouseDown={() => setHolding(true)}
        onMouseUp={() => setHolding(false)}
        onMouseLeave={() => setHolding(false)}
        className='canvas'
        >
            {grid.map((row, rowIndex) => 
                row.map((color, colIndex) => (
                    <div
                    key={`${rowIndex}-${colIndex}`}
                    className='canvas-square'
                    style={{ backgroundColor: color }}
                    onMouseEnter={() => {
                        if(holding) paintCell(rowIndex,colIndex,selectedColor);
                    }}
                    onMouseDown={() => paintCell(rowIndex,colIndex,selectedColor)}
                    >

                    </div>
                ))
            )}
        
        </div>
    )
}