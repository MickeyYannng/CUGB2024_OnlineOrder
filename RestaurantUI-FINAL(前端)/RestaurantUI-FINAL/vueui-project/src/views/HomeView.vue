<template>
  <div>
    <!-- 顶部导航栏 -->
    <TopBar />
    <!-- 主视觉区域 -->
    <section class="hero">
      <div class="hero-overlay" >
         <el-carousel height="450px">
          <el-carousel-item v-for="item in 4" :key="item">
            <h3 class="small justify-center" text="2xl">{{ item }}</h3>
          </el-carousel-item>
        </el-carousel>
        <div class="hero-container">
          <h1 class="hero-title">寻味中华 舌尖美食</h1>
          <p class="hero-subtitle">数千商家，万种美食 触手可得</p>
          <button class="cta-button" @click="goToMenu">立即点餐 →</button>
        </div>
      </div>
    </section>

    <!-- 关于我们模块 -->
    <section class="about">
      <div class="container">
        <h1>关于我们</h1>
        <div class="about-content">
          <div class="about-text">
            <h3>网站介绍与服务理念</h3>
            <br>
            <p>
              我们致力于为用户提供便捷、美味的美食外卖服务。选择严选的商家资源，专业的配送团队和安全保障，为您带来一站式高品质的就餐体验。
            </p>
            <br>
              <li>优质商家严格筛选</li>
              <br>
              <li>专业团队极速送达</li>
               <br>
              <li>食品安全严格把控</li>
          </div>
          <div class="about-stats">
            <h3>合作商家展示与招募</h3>
            <br>
            <p>
              我们诚邀各类优质餐饮商家加入平台，共同打造美食生态圈。提供专业的运营支持，营销推广和配送服务，助力商家实现业务增长。
            </p>
            <div class="stats">
              <div>
                <span class="stat-number">5000+</span>
                <p>合作商家</p>
              </div>
              <div>
                <span class="stat-number">98%</span>
                <p>好评率</p>
              </div>
            </div>
            <button class="partner-button">申请成为合作商家 →</button>
          </div>
        </div>
      </div>
    </section>

    <!-- 页脚 -->
    <footer class="footer">
      <div class="foot-container">
        <div>
          <h4>关于我们</h4>
            <a href="#">公司简介</a>
            <br><br>
            <a href="#">加入我们</a>
             <br><br>
            <a href="#">联系方式</a>
        </div>
        <div>
          <h4>商务合作</h4>
            <a href="#">商家入驻</a>
            <br><br>
            <a href="#">广告合作</a>
             <br><br>
            <a href="#">战略合作</a>
        </div>
        <div>
          <h4>帮助中心</h4>
           <a href="#">常见问题</a>
            <br><br>
            <a href="#">用户协议</a>
             <br><br>
            <a href="#">隐私政策</a>
        </div>
       <div>
          <h4>关注我们</h4>
          <div class="social-icons">
            <a href="https://weixin.qq.com/" target="_blank" rel="noopener noreferrer">
              <i class="fab fa-weixin"></i>
            </a>
            <a href="https://weibo.com/" target="_blank" rel="noopener noreferrer">
              <i class="fab fa-weibo"></i>
            </a>
            <a href="https://im.qq.com/" target="_blank" rel="noopener noreferrer">
              <i class="fab fa-qq"></i>
            </a>
          </div>
        </div>

      </div>
      <div class="footer-bottom">
        <p>© 2024 故乡食间. 保留所有权利.</p>
      </div>
    </footer>
  </div>
</template>

<script setup>
import TopBar from '@/components/TopBar.vue';
import { useRouter } from 'vue-router';

const router = useRouter();
function goToMenu(){
  router.push('/menu');
}
</script>

<style>
/* 全局样式 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;

}
body {
    border:0;
     margin: 0;
  font-family: "Arial", sans-serif;
}


/* 容器 */
.top-container {
  max-width: 100%;
  flex-direction: row;
  display: flex;
  margin: 0 auto;
  padding: 0 1rem;
}

/* 主视觉区域 */
.hero {
  /* background-image: url("../assets/background.png"); */
  background-size: cover;
  color: rgb(255, 255, 255);
  position: relative;
  overflow: hidden;
}
.hero-overlay {
  position: relative;
  height: 100%;
  z-index: 1;
  background-color: rgba(0, 0, 0, 0.113);
}
/* 文字和按钮容器 */
.hero-container{
  position: absolute;
   transform: translate(-0%, -170%);
  z-index: 10; /* 使其在轮播图之上 */
}
.hero-title {
  font-size: 2.5rem;
  margin-top:50px;
  margin-left: 5rem;

}
.hero-subtitle{
  margin-top:20px;
  margin-left: 5rem;
}
.cta-button {
  margin-top:30px;
  margin-left: 5rem;
  width: 200px;
  background-color: #000000;
  color: white;
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

/* 轮播图背景样式 */
.el-carousel__item {
  position: relative;
  z-index: 1; /* 保持轮播图处于文字下方 */
}
.demonstration {
  color: var(--el-text-color-secondary);
}
.el-carousel__item h3 {
  color: #475669;
  /* opacity: 0.75; */
  /* line-height: 150px; */

}
.el-carousel__item:nth-child(4n) {
   background-image: url("../assets/background1.png");
   background-size: cover;
   background-position: center;
}
.el-carousel__item:nth-child(4n + 1) {
  background-image: url("../assets/background2.png");
   background-size: cover;
   background-position: center;
}
.el-carousel__item:nth-child(4n + 2) {
  background-image: url("../assets/background3.png");
   background-size: cover;
   background-position: center;
}
.el-carousel__item:nth-child(4n + 3) {
  background-image: url("../assets/background4.png");
  background-size:cover;
   background-position:center; 
}
/* 关于我们模块 */
.about {
  background-color: #fff;
  padding: 3rem 0;
}
.about .container{
  text-align: center;

}
.about .container h1{
  font-size: 2rem;
  margin-bottom: 2rem;
}
.about-content {
  display: flex;
  flex-direction: row;
  gap: 2em;
}

.about-text,
.about-stats {
  margin-left: 75px;
  width:500px;
  text-align: left;
}

.stats {
  display: flex;
  gap: 3rem;
  margin-top: 1rem;
}

.stat-number {
  margin-left: 100px;
  font-size: 2rem;
  font-weight: bold;
  color: #000000;
}

.stats p {
  margin-left: 110px;
  display: flex;
  gap: 3rem;
  margin-top: 1rem;
}

.partner-button {
  margin-top: 2rem;
  width:500px;
  height:50px;
  background-color: #ffffff;
  color: #000000;
  border: 2px solid black;
  cursor: pointer;
  border-radius: 4px;
}

/* 页脚 */
.footer {
  background-color: #333;
  color: #fff;
  padding: 2rem 0;
  justify-content: space-between; 
}
.foot-container{
  margin-top: 30px;
  margin-bottom: 100px;
  margin-left: 160px;
  display: flex;
}
.foot-container div{
   flex: 1; 
}
.footer h4 {
  margin-bottom: 1rem;
}

.footer a {
  color: #bbb;
  text-decoration: none;
}

.footer a:hover {
  color: #fff;
}

.footer-bottom {
  text-align: center;
  margin-top: 1rem;
  border-top: 1px solid #555;
  padding-top: 1rem;
}

.social-icons {
  display: flex;
  gap: 1rem;
  justify-content: flex-start; /* 图标对齐方式 */
}

.social-icons a {
  font-size: 1.5rem;
  color: #ffffff;
  text-decoration: none;
  transition: color 0.3s;
}

.social-icons a:hover {
  color: #ff80aa67; 
}

</style>
