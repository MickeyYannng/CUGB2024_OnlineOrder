<template>
    <div class="header">
        <el-container class="top-container">
          <el-aside>
            <img class="logo" src="../assets/logo.png" @click="goToHome">
          </el-aside>
          <div class="search-container">
            <input
              v-model="searchQuery"
              type="text"
              placeholder="搜索菜品..."
              class="search-input"
            />
            <button class="search-button" @click="handleSearch">
              <svg class="search-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
                <path d="M0 0h24v24H0V0z" fill="none"/>
                <path d="M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z"/>
              </svg>
            </button>
          </div>

          <el-aside class="auth-buttons"> 
             <router-link to="/login">
              <el-button class="auth-but1">登录</el-button>
            </router-link>
            <router-link to="/register">
            <el-button class="auth-but2">注册</el-button>
            </router-link>
          </el-aside>

            <div class="demo-type">
              <!-- 使用头像图片作为按钮 -->
              <img
                src="../assets/person.png"
                alt="Avatar"
                class="avatar-button"
                @click="goToAdminControl"
              />
            </div>

        </el-container>
    </div>
</template>

<script setup>
import { ref } from 'vue';
// import { UserFilled } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router';
const router = useRouter();
const searchQuery = ref('');

const handleSearch = () => {
  console.log('Searching for:', searchQuery.value);
  // 在此可以进行更复杂的搜索逻辑，比如通知父组件更新菜品列表
};
const goToAdminControl = () => {
  console.log('goToAdminControl clicked'); // 输出调试信息
  try {
    window.location.href = "http://172.20.84.75:8081/UserProfile";
  } catch (error) {
    console.error('Navigation error:', error); // 如果跳转失败，输出错误信息
  }
};


function goToHome(){
 router.push('/');
}
</script>

<style>
.header {
  background-color: #ffffff;
  border-bottom: 1px solid #e0e0e0;
  display: flex; 
  justify-content: space-between; 
  align-items: center; 
}

.logo {
  cursor: pointer;
  width: 150px;
}

.auth-buttons .auth-but1 {
  margin-left: 150px; 
  margin-top: 25px; 
  padding: 0.5rem 1rem;
  background-color: #ffffff;
  color: #000000;
  border: 1px solid #000000;
  border-radius: 4px;
  cursor: pointer;
}
.auth-buttons .auth-but2 {
  margin-left: 10px; 
  margin-top: 25px; 
  padding: 0.5rem 1rem;
  background-color: #000000;
  border: 1px solid #000000;
  color: #ffffff;
  border-radius: 4px;
  cursor: pointer;
}

.auth-buttons .auth-but1:hover{
  background-color: #ff80aa85; 
  color: #000000; /* 白色文字 */
  border-color: #ff80aa85; 
}
.auth-buttons .auth-but2:hover {
  background-color: #ff80ab; 
  color: #ffffff; /* 白色文字 */
  border-color: #ff80ab; 
}
.search-container {
  display: flex;
  border: 2px solid #e8e8e8bd;
  border-radius: 4px;
  margin-top: 20px;
  margin-bottom: 20px;
  margin-left: 30px;
  width:500px;
}

.search-input {
  margin-left:10px;
  border: none;
  width:450px;
  font-size: 14px;
  outline: none;
}

.search-button {
  background: none;
  border: none;
  cursor: pointer;
  padding: 2px 10px;
  display: flex;
  align-items: center;
}

.search-icon {
  width: 30px;
  height: 30px;
}

.avatar-button{
  width:30px;
  height:30px;
  cursor: pointer;
  margin-top: 25px;
  margin-left:23px;
  
}
</style>