import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: 路由配置项
 *
 * hidden: true                   // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 * alwaysShow: true               // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 *                                // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 *                                // 若你想不管路由下面的 children 声明的个数都显示你的根路由
 *                                // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 * redirect: noRedirect           // 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'             // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * meta : {
    noCache: true                // 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
    title: 'title'               // 设置该路由在侧边栏和面包屑中展示的名字
    icon: 'svg-name'             // 设置该路由的图标，对应路径src/assets/icons/svg
    breadcrumb: false            // 如果设置为false，则不会在breadcrumb面包屑中显示
  }
 */

// 公共路由
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: (resolve) => require(['@/views/redirect'], resolve)
      }
    ]
  },
  {
    path: '/login',
    component: (resolve) => require(['@/views/login'], resolve),
    hidden: true
  },
  {
    path: '/register',
    component: (resolve) => require(['@/views/register'], resolve),
    hidden: true
  },
  {
    path: '',
    component: Layout,
    redirect: 'home',
    children: [
      {
        path: 'home',
        component: (resolve) => require(['@/views/home'], resolve),
        name: '首页',
        meta: { title: '首页', icon: 'dashboard', noCache: true, affix: true }
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'profile',
        component: (resolve) => require(['@/views/system/user/index'], resolve),
        name: 'Profile',
        meta: { title: '个人中心', icon: 'user' }
      },
      {
        path: 'notice',
        component: (resolve) => require(['@/views/system/notice/index'], resolve),
        name: 'notice',
        meta: { title: '消息中心', icon: 'user' }
      },
      {
        path: 'resetPwd',
        component: (resolve) => require(['@/views/system/resetPwd/index'], resolve),
        name: 'resetPwd',
        meta: { title: '修改密码', icon: 'user' }
      }
    ]
  },
  {
    path: '/news',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'comment',
        component: (resolve) => require(['@/views/news/comment/index'], resolve),
        name: 'comment',
        meta: { title: '评论列表' }
      },
      {
        path: 'detail',
        component: (resolve) => require(['@/views/news/detail/index'], resolve),
        name: 'detail',
        meta: { title: '详情' }
      },
      {
        path: 'knowledge',
        component: (resolve) => require(['@/views/news/knowledge/index'], resolve),
        name: 'knowledge',
        meta: { title: '知识库' }
      },
      {
        path: 'collect',
        component: (resolve) => require(['@/views/news/collect/index'], resolve),
        name: 'table',
        meta: { title: '用户新闻列表' }
      }
    ]
  }
]

export default new Router({
  mode: 'history', // 去掉url中的#
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})
