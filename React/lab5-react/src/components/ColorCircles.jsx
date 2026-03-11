import './ColorCircles.css';

export default function ColorCircles({ colorArr, selectedColor, setSelectedColor }) {
    
    return (
        <div className="color-circles">
            {colorArr.map((color) => {
                return (
                    <div key={color.id} 
                    className={`color-circle ${selectedColor === color ? 'selected' : ''}`}
                    style={{ backgroundColor: color.color }} 
                    onClick={() => setSelectedColor(color)}>
                    </div>
                )
            })}
        </div>
    )

}