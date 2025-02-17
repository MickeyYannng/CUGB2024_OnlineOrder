<template>
  <div class="total">
    <div class="registration-form">
      <h2 class="form-title">创建新账号</h2>
      <p style="text-align: center;">请填写基本信息完成注册</p>
      <div class="form-group">
        <label for="username">用户名</label>
        <input type="text" id="username"
         v-model="form.username" 
         placeholder="请输入用户名"
         @blur="isSame" />
      </div>
      <div class="form-group">
        <label for="phone">手机号</label>
        <input type="text" id="phone" v-model="form.phone" placeholder="请输入手机号" />
      </div>
      <div class="form-group">
        <label for="email">邮箱</label>
        <input type="email" id="email" v-model="form.email" placeholder="请输入邮箱" />
      </div>
      <div class="form-group">
        <label for="password">密码</label>
        <input type="password" v-model="form.password" placeholder="请输入密码" />
      </div>
      <div class="form-group">
        <label for="confirmPassword">确认密码</label>
        <input type="password" v-model="form.confirmPassword" placeholder="请确认密码" />
      </div>
      <div class="form-group">
        <label for="captcha">验证码</label>
        <div class="captcha-container">
          <input type="text" style="width: 55%;" id="captcha" v-model="form.captcha" placeholder="请输入验证码" />
          <button type="button" style="width: 40%;" @click="sendCaptcha">获取验证码</button>
        </div>
      </div>
      <div class="form-group" style="text-align: center;">
        <button type="button" @click="register">注册</button>
      </div>
      <div class="login-link">
        已有账号？<router-link to="/login">立即登录</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

export default {
  setup() {
    const form = reactive({
      username: '',
      phone: '',
      email: '',
      password: '',
      confirmPassword: '',
      captcha: '',
    });

    const router = useRouter();
    // 检查用户名是否已存在
    const isSame = () => {
      axios
        .get(`http://43m93z.natappfree.cc/users/byname/${form.username}`)
        .then((response) => {
          if (response.data.data !== null) {
            // 用户名已存在，返回错误信息
            alert('用户名已存在');
          } else {
            // 用户名不存在，可以在这里调用注册方法
            // this.Finalregister();  // 你可以根据需求取消注释来触发注册逻辑
          }
        })
        .catch((error) => {
          console.log(error);
          alert('检查用户名时发生错误，请稍后重试。');
        });
    };
    const sendCaptcha = () => {
      alert('验证码已发送，验证码为：1234');
    };

    const register = () => {
      if (form.password !== form.confirmPassword) {
        alert('两次输入的密码不一致！');
        return;
      }
      if (form.captcha === '1234') {
        axios
          .post('http://43m93z.natappfree.cc/register', {
            username: form.username,
            phone: form.phone,
            email: form.email,
            password: form.password,
            captcha: form.captcha,
          })
          .then((response) => {
            if (response.data.code === 1 && response.data.msg === 'success') {
              alert('注册成功');
              router.push('/login');
            } else {
              alert('注册失败，请检查输入并稍后重试。');
              router.push('/register');
            }
          })
          .catch(() => {
            alert('注册失败，请检查输入并稍后重试。');
            router.push('/register');
          });
      } else {
        alert('验证码错误');
      }
    };

    return {
      form,
      sendCaptcha,
      register,
      isSame, 
    };
  },
};
</script>

<style scoped>
body {
  font-family: 'Noto Sans SC', sans-serif;
  background-color: #f5f5f5;
}
.total{
  background-image: url("../assets/background3.png");
  background-size: cover; 
}

.registration-form {
  width: 450px;
  margin-left: 400px;
  background-color: rgba(255, 255, 255, 0.678);
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.024);
}

.form-title {
  text-align: center;
  margin-bottom: 30px;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
}

.form-group input {
  width: 93.5%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f5f5f5; /* 灰色背景 */
}

.form-group button {
  width: 100%;
  padding: 10px;
  background-color: black; /* 黑色背景 */
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.captcha-container {
  display: flex;
  justify-content: space-between; /* 使输入框和按钮分开 */
  width: 100%; /* 调整为100%宽度 */
}

.form-group button:hover {
  background-color: #333;
}

.login-link {
  text-align: center;
  margin-top: 20px;
}

.login-link a {
  color: #007bff; /* 蓝色链接 */
  text-decoration: none;
}

.login-link a:hover {
  text-decoration: underline; /* 鼠标悬停时添加下划线 */
}

</style>