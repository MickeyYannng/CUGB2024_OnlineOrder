<template>
  <div style="font-family: 'Noto Sans SC';" class="total">
    <div class="container">
      <div class="login-box">
        <div class="header">
          <img src="@/assets/logo.png" alt="Logo" />
          <h2>欢迎回来</h2>
          <p>请登录您的账户</p>
        </div>
        <div class="form-container">
          <form @submit.prevent="handleLoginSubmit">
            <div class="input-group">
              <label>用户名</label>
              <div class="input-wrapper">
                <i class="fa fa-user form-control-feedback"></i>
                <input type="text" required v-model="input" placeholder="请输入您的用户名" />
              </div>
            </div>
            <div class="input-group">
              <label>密码</label>
              <div class="input-wrapper">
                <i class="fas fa-lock form-control-feedback"></i>
                <input type="password" required v-model="password" placeholder="请输入您的密码" />
              </div>
            </div>
            <div class="input-group">
              <label>验证码</label>
              <div class="input-wrapper">
                <i class="fa-solid fa-shield form-control-feedback"></i>
                <input type="text" v-model="captchaInput" placeholder="请输入验证码" />
                <img :src="captchaImage" @click="refreshCaptcha" alt="验证码图片" />
              </div>
            </div>
            <div class="options">
              <div class="remember-me">
                <input type="checkbox" id="remember-me" v-model="rememberMe" />
                <label for="remember-me">记住我</label>
              </div>
              <a href="#" @click.prevent="showForgotPasswordModal">忘记密码？</a>
            </div>
            <button type="submit" class="login-button">登录</button>
          </form>
          <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
        </div>
        <p class="register-link">
          还没有账户？
          <router-link to="/register">立即注册</router-link>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

export default {
  name: 'LoginPage',
  setup() {
    const router = useRouter();
    const input = ref('');
    const password = ref('');
    const rememberMe = ref(false);
    const captchaInput = ref('');
    const captcha = ref('');
    const captchaImage = ref('');
    const errorMessage = ref('');
    const isLoading = ref(false); // 加载状态

    // 刷新验证码
    const refreshCaptcha = () => {
      const characters = 'ABCDEFGHJKLMNOPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz0123456789';
      let captchaText = '';
      for (let i = 0; i < 5; i++) {
        captchaText += characters.charAt(Math.floor(Math.random() * characters.length));
      }
      const canvas = document.createElement('canvas');
      const ctx = canvas.getContext('2d');
      canvas.width = 100;
      canvas.height = 40;
      ctx.font = '20px Arial';
      ctx.fillStyle = '#f9f9f9';
      ctx.fillRect(0, 0, canvas.width, canvas.height);
      ctx.fillStyle = '#000';
      ctx.textAlign = 'center';
      ctx.textBaseline = 'middle';
      ctx.fillText(captchaText, canvas.width / 2, canvas.height / 2);
      captchaImage.value = canvas.toDataURL();
      captcha.value = captchaText;
    };
     // 登录表单提交
    const handleLoginSubmit = async () => {
      errorMessage.value = '';
      isLoading.value = true; // 开启加载状态
      // 检查用户名是否为 admin
      if (input.value === 'admin') {
        console.log('Admin user detected. Redirecting...');
        window.location.href = 'http://172.20.84.75:8081/AdminControl'; // 跳转到 admin 页面
        isLoading.value = false; // 请求完成，关闭加载状态
        return; // 终止后续操作
      }
      // 验证验证码
      if (captchaInput.value !== captcha.value) {
        errorMessage.value = '验证码错误';
        refreshCaptcha();
        isLoading.value = false; // 关闭加载状态
        return;
      }
      // 检查用户名和密码是否为空
      if (!input.value || !password.value) {
        errorMessage.value = '用户名和密码不能为空';
        isLoading.value = false; // 关闭加载状态
        return;
      }
      // 调用登录接口
      try {
        const response = await axios.post('http://43m93z.natappfree.cc/login', {
          username: input.value,
          password: password.value,
        });
        if (response.data.code === 1) {
          router.push({ name: 'home' }); // 跳转到 Home 页面
        } else {
          errorMessage.value = response.data.message || '登录失败，请检查您的用户名和密码';
        }
      } catch (error) {
        errorMessage.value = error.response?.data?.message || '登录请求发生错误';
      } finally {
        isLoading.value = false; // 关闭加载状态
      }
    };

    // 初始加载验证码
    onMounted(() => {
      refreshCaptcha();
    });

    return {
      input,
      password,
      rememberMe,
      captchaInput,
      captchaImage,
      errorMessage,
      refreshCaptcha,
      handleLoginSubmit,
      isLoading,
    };
  },
};
</script>

<style scoped>
.total{
  background-image: url(../assets/background6.png);
  background-size: cover; 
}
.container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 1rem;
}

.login-box {
  width: 100%;
  max-width: 30rem;
  background-color: rgba(255, 255, 255, 0.716);
  padding: 2rem;
  border-radius: 0.5rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.header {
  background-color: rgba(255, 255, 255, 0);
  display: flex;
  flex-direction: column;  /* 使子元素竖向排列 */
  align-items: center;     /* 居中对齐 */
  text-align: center;
  margin-bottom: 1rem;
  border-radius: 8px;
}

.header img {
  height: 6rem;
  margin-bottom: 0rem;
}

.header h2 {
  font-size: 1.5rem;
  font-weight: bold;
  color: #222222;
}

.header p {
  margin-top: 0.5rem;
  font-size: 0.875rem;
  color: #71717A;
  margin-bottom: 5px;
}

.input-group {
  margin-bottom: 1.5rem;
}

.input-group label {
  display: block;
  font-size: 0.875rem;
  font-weight: medium;
  color: #474747;
  margin-bottom: 0.5rem;
}

.input-wrapper {
  position: relative;
  display: flex;
  align-items: center; /* 使图标和输入框垂直居中 */
}

.input-wrapper input {
  flex-grow: 1; /* 使输入框占据剩余空间 */
  padding-left: 2.5rem; /* 为图标留出空间 */
  border-radius: 0.375rem;
  border: 2px solid #ccc;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  outline: none;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
  height: 3rem;
}

.input-wrapper .form-control-feedback {
  position: absolute;
  left: 0.75rem; /* 调整图标位置 */
  top: 50%;
  transform: translateY(-50%);
  color: #0a0000;
}

.input-wrapper input:focus {
  border-color: #007bff;
}

.options {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 50px;
}

.remember-me {
  display: flex;
  align-items: center;
}

.remember-me input {
  height: 1rem;
  width: 1rem;
  border: 1px solid #d1d1d1;
  border-radius: 0.25rem;
  outline: none;
  transition: border-color 0.15s ease-in-out;
}

.remember-me label {
  margin-left: 0.5rem;
  font-size: 0.875rem;
  color: #474747;
}

.login-button {
  width: 100%;
  display: flex;
  justify-content: center;
  padding: 0.75rem 1rem;
  border-radius: 0.375rem;
  background-color: black;
  color: white;
  text-decoration: none;
  transition: background-color 0.15s ease-in-out;
}

.login-button:hover {
  background-color: #535454;
}

.error-message {
  margin-top: 1rem;
  color: red;
}

.register-link {
  margin-top: 2rem;
  text-align: center;
  font-size: 0.875rem;
  color: #71717A;
}

.register-link a {
  font-weight: medium;
  color: #007bff;
  text-decoration: none;
  transition: color 0.15s ease-in-out;
}

.register-link a:hover {
  text-decoration: underline;
  color: #0056b3;
}

.verification-modal {
  display: flex;
  position: fixed;
  top: 0;
  left: 0;
  background-color: rgba(70, 70, 70, 0.5);
  overflow-y: auto;
  height: 100%;
  width: 100%;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.modal-content {
  position: relative;
  background-color: white;
  border-radius: 0.5rem;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  max-width: 30rem;
  width: 100%;
  margin: 1rem;
  padding: 1.5rem;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.modal-header h3 {
  font-size: 1.25rem;
  font-weight: medium;
  color: #222222;
}

.close-button {
  background: none;
  border: none;
  color: #71717A;
  cursor: pointer;
  transition: color 0.15s ease-in-out;
}

.close-button:hover {
  color: #474747;
}

.captcha-group {
  margin-bottom: 2rem;
}

.captcha-input-wrapper {
  position: relative;
}

.captcha-input-wrapper input {
  width: 80%;
  padding-right: 3rem;
  border-radius: 0.375rem;
  border: 2px solid #999999;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  outline: none;
  transition: border-color 0.15s ease-in-out;
  height: 4rem;
}

.captcha-input-wrapper input:focus {
  border-color: #007bff;
}

.captcha-input-wrapper img {
  position: absolute;
  top: 50%;
  right: 0.5rem;
  transform: translateY(-50%);
  height: 2rem;
  cursor: pointer;
}

.submit-button {
  width: 100%;
  display: flex;
  justify-content: center;
  padding: 0.75rem 1rem;
  border-radius: 0.375rem;
  background-color: #007bff;
  color: white;
  transition: background-color 0.15s ease-in-out;
}

.submit-button:hover {
  background-color: #00629f;
}

.modal {
  display: flex;
  position: fixed;
  top: 0;
  left: 0;
  background-color: rgba(70, 70, 70, 0.5);
  height: 100%;
  width: 100%;
  align-items: center;
  justify-content: center;
}

.modal-content {
  background-color: white;
  border-radius: 0.5rem;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 1.5rem;
  max-width: 400px;
  width: 90%;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.close-button {
  background: none;
  border: none;
  color: #71717A;
  cursor: pointer;
  transition: color 0.15s ease-in-out;
}

.close-button:hover {
  color: #474747;
}

.modal-body {
  display: flex;
  flex-direction: column;
}

.modal-body label {
  margin-bottom: 0.5rem;
  font-size: 0.875rem;
}

.modal-body input {
  border: 2px solid #ccc;
  border-radius: 0.375rem;
  padding: 0.5rem;
  margin-bottom: 1rem;
}

.send-button {
  background-color: #000408;
  color: white;
  border: none;
  padding: 0.5rem;
  border-radius: 0.375rem;
  cursor: pointer;
  transition: background-color 0.15s ease-in-out;
}

.send-button:hover {
  background-color: #3e3e3f;
}

.verification-message {
  margin-top: 0.5rem;
  color: #28a745;
  /* 绿色用于成功消息 */
}

</style>