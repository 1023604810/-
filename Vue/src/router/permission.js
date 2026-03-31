//权限控制模块
import router from "./index.js"

//动态路由规则
const dynamicRoutes = [
    // 个人信息路由
    {
        path: "info",
        name: "info",
        // 路由的懒加载，提高性能
        component: () => import('../components/Info.vue'),
        meta: {
            // 标识是否是二级路由
            isChildPage: true,
            title:"个人信息"
        }
    }
]

// 路由前置守卫判断登录权限
router.beforeEach((to, from, next) => {
    //获取离线存储token
    const token = sessionStorage.getItem("token")
    // 使用JSON.parse将router转变成对象类型
    const rules = JSON.parse(sessionStorage.getItem("router"))
    // console.log(typeof(rules))
    if (token) {
        // 检查rules是否为null或undefined，如果是，使用空数组替代
        const validRules = rules || [];
        // 根据本地存储用户权限过滤出相应的动态路由
        const routerRules = dynamicRoutes.filter(item=>validRules.includes(item.name))
        // 把过滤出来的动态路由添加到路由表中
        routerRules.forEach(item => {
            // 增加到mine子路由下
            router.addRoute("mine", item)
        })
        next()
        return
    }
    else {
        //如果不存在token，访问个人中心则被强制跳转登录页
        if (to.name == "mine") {
            next({ name: "login" })
            return
        }
        //除个人中心以外的页面被放行
        else {
            next()
            return
        }
    }

})

//路由后置守卫修改页面标题
router.afterEach(to => {
    document.title = to.meta.title
})


export default router