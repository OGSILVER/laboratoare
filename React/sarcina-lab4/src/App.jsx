import devicesF from "./assets/devices.json"
import Device from './Device'

function App() {
  let devices = devicesF



  return (
    
    <>
    <ul>
      {console.log(devices)}
      {devices.map((device) => (
        <li key={device.id}>
          <Device name={device.name} type={device.type} statusL={device.status} loadL={device.load}/>
        </li>
      ))}

    </ul>

  
      
    </>
  )
}

export default App
