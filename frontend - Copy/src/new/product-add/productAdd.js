
import axios from "axios";
import {Component} from "react";
import ProductSpecInput from "./ProductSpecInput";




class ProductAdd extends Component{

    constructor() {
        super();
        this.state = {
            categories: [],
            selectedCategory: {}
        }
    }

onSubmit=()=>{}
    componentDidMount() {
        axios.get(`http://localhost:8888/categories`)
            .then(response=> console.log(response))
    }

    render() {
        return <div>
        {/*// <div id={"specs"} className={"specs-container"}>*/}
        {/*//         {this.state.specs.map((s, index) => <ProductSpecInput key={s.id} count={index}/>)}*/}
        {/*//     </div>*/}
            <button onClick={this.addSpecInput}>Add spec</button>

            <div>
                <input type={"file"} accept={".jpeg,.jpg,.png"} id={"image-input"}/>
            </div>
            <label > Quantity:<input name={"quantity"}/></label>
            <label >Price: <input name={"price"}/></label>
            <label >Add producer: <input name={"producer"}/></label>
            <button onClick={this.onSubmit}>Submit</button></div>
    }
}
export  default ProductAdd;