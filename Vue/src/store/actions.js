import axios from "axios"
export default{
    getState(context,item){
        item.readNum+=1
        axios.put("http://localhost:8081/api/articles/"+item.id,item)
        .then(data=>{
            if(data.status===200){
                context.commit('store',data.data)
            }
        })
    }
}