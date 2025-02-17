import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import DishDetails from '@/views/DishDetails.vue'
import DishMenu from '@/views/DishMenu.vue'
import LoginPage from '@/views/LoginPage.vue';
import RegisterPage from '@/views/RegisterPage.vue';
import MyCart from '@/views/MyCart.vue';
import AdminControl from '@/views/AdminControl.vue';
import UserProfile from '@/views/UserProfile.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/detail',
    name: 'detail',
    component: DishDetails
  },
  {
    path: '/menu',
    name: 'menu',
    component: DishMenu
  },
  {
    path: '/detail/:dishid', // 路径带参数的方式
    name: 'DishDetails',
    component: DishDetails,  // 对应组件
  },
  {
    path: '/login',
    name: 'login',
    component: LoginPage,
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterPage,
  },
  {
    path: '/cart',
    name: 'cart',
    component: MyCart,
  },
  {
    path: '/user',
    name: 'user',
    component: UserProfile,
  },
  {
    path: '/admin',
    name: 'admin',
    component: AdminControl,
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
