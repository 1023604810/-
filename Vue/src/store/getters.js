export default{
    hot(state){
        const n=Math.ceil(state.overview.readNum/100)
        return n<5?n:5
    }
}