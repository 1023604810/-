// 1 导入包
import { createWebHistory ,createRouter } from "vue-router";

// 2 导入路由组件
import Articles from "../pages/Articles"
import MessageBoard from "../pages/MessageBoard"
import List from "../components/List"
import Grid from "../components/Grid"
import SingleArticle from "../components/SingleArticle"
import Login from "../pages/Login"
import Register from "../pages/Register"
import Mine from "../pages/Mine"
import Layout from "../Layout"
import Comments from "../pages/Comments"
import Billboards from "../pages/Billboards"
import Undefined from "../components/404.vue"

// 3 制定路由规则
const routes = [
    {path:"/",redirect:"/login"},
    {path:"/article/:id",name:"article",component:SingleArticle,meta:{title:"热点文章"}},
    {path:"/articles",name:"hot",component:Articles,
    children:[
        {path:"/articles",redirect:"/articles/list"},
        {path:"list",name:"list",component:List},
        {path:"grid",name:"grid",component:Grid},

    ],meta:{title:"热点文章"}},
    {path:"/messageboard",name:"messageboard",component:MessageBoard,meta:{title:"畅游福建"}},
    {path:"/login",name:"login",component:Login,meta:{title:"个人登录"}},
    {path:"/register",name:"register",component:Register,meta:{title:"用户注册"}},
    {path:"/mine",name:"mine",component:Mine,meta:{title:"个人中心"}},
    {path:"/admin",name:"admin",component:Comments,meta:{title:"留言管理"}},
    {path:"/404",name:"404",component:Undefined,meta:{title:"404"}},
    {path:"/:pathMatch(.*)",redirect:"/404"}
]


// 4 使用工厂函数创建路由
const router = createRouter({
    linkActiveClass:"link-dark",
    history:createWebHistory(),
    routes:routes
})




// 导出路由
export default router