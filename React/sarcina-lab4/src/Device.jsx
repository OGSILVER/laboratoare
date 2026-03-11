import "./Device.css"
import {useState} from "react"

function Device({name, type, statusL, loadL}){
    const [status,setStatus] = useState(statusL)
    const [load,setLoad] = useState(loadL)
    

    function addLoad(num){

        if(load+num<0){
            setLoad(0);
        }else if(load+num>100){
            setLoad(100);
        }else{
            setLoad(load+num)
        }
    
        if(load<=60 && load>=0){
        setStatus("Active");
        }else if(load <= 90 && load > 60){
        setStatus("Warning");
        }else{
        setStatus("Critical");
        }


    }

    

    return (
        <>
        <div className={status}>
            <p>{name}</p>
            <p>{type}</p>
            <p>{status}</p>
            <p>{load}</p>
            <div className="button" onClick={() => addLoad(10)}>Add Traffic</div>
            <div className="button" onClick={() => addLoad(-20)}>Clear Traffic</div>
        </div>
            
        </>
    );
}





export default Device;