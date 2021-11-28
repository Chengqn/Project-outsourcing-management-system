import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/login/login.vue'
import Home from '@/components/home/home.vue'
import Users from '@/components/users/users.vue'
import Right from '@/components/rights/right.vue'
import Role from '@/components/rights/role.vue'
import Leave from '@/components/leave/leaveList.vue'
import ApplyForLeave from '@/components/leave/applyForLeave.vue'
import Project from '@/components/project/project.vue'
import MyProject from '@/components/project/myproject.vue'
import Mission from '@/components/project/mission.vue'
import Pubproject from '@/components/project/Pubproject.vue'
import TestRole from '@/components/login/testRole.vue'
import Pubhome from '@/components/puber/pubhome.vue'
import Puberproject from '@/components/puber/puberproject.vue'
import Pubermyproject from '@/components/puber/pubermyproject.vue'
import PuberMission from '@/components/puber/puberMission.vue'
import AcceptProject from '@/components/puber/acceptProject.vue'
import Userhome from '@/components/home/userhome.vue'
import AcceptedProject from '@/components/project/acceptedProject.vue'
import Usermission from '@/components/home/Usermission.vue'


Vue.use(Router)

export default new Router({
  routes: [
    //登陆路由
    {
      name: 'login',
      path: '/login',
      component: Login

    },
    //判断权限的路由
    {
      name: 'testRole',
      path: '/testRole',
      component: TestRole
    },
    //首页路由
    //开发人员路由
    {
      name: 'userhome',
      path: '/userhome',
      component: Userhome,
      children: [
        {
          name: 'usermission',
          path: '/usermission',
          component: Usermission,
        }

      ]
    },
    //项目发布者的路由
    {
      name: 'pubhome',
      path: '/pubhome',
      component: Pubhome,
      children: [
        {
          name: 'puberproject',
          path: '/puberproject',
          component: Puberproject,
        },
        {
          name: 'pubermyproject',
          path: '/pubermyproject',
          component: Pubermyproject,
        },
        {
          name: 'puberMission',
          path: '/puberMission',
          component: PuberMission,
        },
        {
          name: 'acceptProject',
          path: '/acceptProject',
          component: AcceptProject,

        },
      ]

    },
    {
      //承包商管理员的界面
      name: 'home',
      path: '/',
      component: Home,
      children: [
        {
          name: 'users',
          path: '/users',
          component: Users
        },
        {
          name: 'rights',
          path: '/rights',
          component: Right
        },
        {
          name: 'roles',
          path: '/roles',
          component: Role
        },
        {
          name: 'leaves',
          path: '/leaves',
          component: Leave
        },
        {
          name: 'applyForLeaves',
          path: '/applyForLeaves',
          component: ApplyForLeave
        },
        {
          name: 'project',
          path: '/project',
          component: Project
        },
        {
          name: 'myproject',
          path: '/myproject',
          component: MyProject
        },
        {
          name: 'missiom',
          path: '/mission',
          component: Mission
        },
        {
          name: 'pubproject',
          path: '/pubproject',
          component: Pubproject
        },
        {
          name: 'acceptedProject',
          path: '/acceptedProject',
          component: AcceptedProject
        },


      ]
    }
  ]
})
