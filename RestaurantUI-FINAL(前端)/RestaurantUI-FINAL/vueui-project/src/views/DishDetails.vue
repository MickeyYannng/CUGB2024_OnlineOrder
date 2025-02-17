<template>
  <TopBar />
  <div class="product-page">
    <!-- 商品详情区域 -->
    <div class="product-container">
      <!-- 左侧大图 -->
        <div class="product-image">
        <img :src="dish.img" alt="dish.name" />
      </div>

      <!-- 右侧商品信息 -->
      <div class="product-info">
        <div class="button-container">
          <el-button 
            type="outlined" 
            class="back-button" 
            @click="goToDishMenu">
            回到菜品概览
          </el-button>
        </div>
      
      <h1>{{ dish.name }}</h1>
        <p class="price">￥ {{ dish.price }}</p><br>
        <p class="description">{{ dish.description }}</p>

        <!-- 数量选择 -->
        <div class="quantity-control">
          <span>数量</span>
          <el-input-number 
            v-model="quantity" 
            :min="0" 
            :max="99" 
            @change="handleQuantityChange" 
            size="middle"
            class="custom-input-number">
          </el-input-number>
        </div>

        <!-- 加入购物车按钮 -->
        <button class="add-to-cart" @click="addToCart">
          <i class="fas fa-shopping-cart"></i> 加入购物车
        </button>

       <!-- 详细信息 -->
        <div class="details">
          <h3>详细信息</h3><br>
          <div :style="{ fontWeight: 'bold' }">主要食材：</div><br>
          <p>{{ dish.ingredients }}</p><br>
          <div :style="{ fontWeight: 'bold' }">营养成分：</div><br> 
          <p>{{ dish.nutrition }}</p><br>
          <div :style="{ fontWeight: 'bold' }">烹饪工艺：</div><br> 
          <p>{{ dish.cookingMethod }}</p>
        </div>
      </div>
    </div>
  </div>
    <div class="footer-bottom">
        <p>© 2024 故乡食间. 保留所有权利.</p>
      </div>
</template>

<script>
import { ElInputNumber } from 'element-plus';
import TopBar from '@/components/TopBar.vue';
import axios from 'axios';

export default {
  name: "QuantityControl",
  components: {
    ElInputNumber,
    TopBar, // 注册 TopBar 组件
  },
  data() {
    return {
      quantity: 1, // 初始数量
      dish: {}, // 存储菜品信息
      userId: 1, // 假设用户ID为1，你可以根据实际情况获取
    };
  },
 methods: {
    handleQuantityChange(value) {
      console.log("当前数量为：", value);
    },
    // 发送请求，添加菜品到购物车
    addToCart() {
      const dishId = this.$route.params.dishid; // 从路由参数中获取 dishid
      console.log("userId:", this.userId);  // 检查 userId
      console.log("dishid:", dishId);  // 检查从路由中获取的 dishid
      if (!dishId) {
        alert("菜品信息缺失，无法添加到购物车！");
        return;
      }
      const params = {
        userid: this.userId,
        dishid: dishId, // 使用从路由中获取的 dishid
      };
      axios.post('http://43m93z.natappfree.cc/cart/add', null, { params })
        .then(response => {
          if (response.data.code === 1) {
            alert(`已将 ${this.quantity} 件 ${this.dish.name} 加入购物车！`);
          } else {
            alert('加入购物车失败，请重试');
          }
        })
         .catch(error => {
            if (error.response) {
              console.error('Error response:', error.response);
              alert(`请求失败: ${error.response.data.msg || '未知错误'}`);
            } else {
              console.error('Error message:', error.message);
              alert('请求失败，请重试');
            }
          });
    },
    // 跳转到菜品概览页面
    goToDishMenu() {
      this.$router.push('/menu'); // 使用 this.$router 来跳转
    },
    // 加载菜品信息
    loadDishDetails() {
      const dishId = this.$route.params.dishid; // 从路由参数获取菜品ID
      axios.get(`http://43m93z.natappfree.cc/dishes`, {
        params: { id: dishId }
      })
      .then(response => {
        const dishData = response.data.data.find(dish => dish.dishid === parseInt(dishId));
        if (dishData) {
          this.dish = {
            name: dishData.name,
            price: dishData.price,
            description: dishData.description,
            ingredients: "五花肉、生抽、老抽、料酒、八角、桂皮、香叶、冰糖", // 示例数据
            nutrition: "热量：350千卡/100g，蛋白质：12g/100g，脂肪：30g/100g", // 示例数据
            cookingMethod: "慢炖2小时，入味香浓", // 示例数据
            img: dishData.img, 
          };
        }
      })
      .catch(error => {
        console.error('Error fetching dish details:', error);
      });
    }
  },
  mounted() {
    this.loadDishDetails(); // 页面加载时自动加载菜品详情
  }
};
</script>

<style scoped>
.product-page {
  display: flex;
  justify-content: center;
  margin-left: 50px;
  margin-top: 30px;
  font-family: Arial, sans-serif;
}
.button-container {
  display: flex;
  justify-content: flex-end; /* 让按钮靠近右侧 */
  margin-right: 100px;
}

.back-button {
  border: 1px solid black; /* 黑色边框 */
  background-color: white; /* 白色填充 */
  color: black; /* 黑色字体 */
}

.back-button:hover {
  background-color: #ff80bf; /* hover时按钮背景色变化为粉色 */
  border-color: #ff80bf; /* hover时按钮边框变成粉色 */
 color: black; /* 黑色字体 */
}

.product-container {
  display: flex;
  width: 100%;
  gap: 60px;
  margin-left:50px;
}

.product-image img {
  margin-top:30px;
  width: 450px;
  height: 350px;
  border-radius: 10px;
  object-fit: cover; /* 保持图片内容适配框 */
}

.product-thumbnails {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}

.product-thumbnails img {
  width: 80px;
  height: 80px;
  border-radius: 5px;
  cursor: pointer;
  object-fit: cover;
}

.product-info {
  flex: 1;
}

.product-info h1 {
  margin-bottom: 10px;
}

.price {
  font-size: 24px;
  color: #e53935;
  margin-bottom: 10px;
}

.description {
  margin-bottom: 20px;
  margin-right: 80px;
}

.quantity-control {
  display: flex;
  align-items: center;
  gap: 10px;

}

.quantity-control span {
  font-size: 16px;
}
/* 调整输入框的宽度 */
.custom-input-number {
  width: 120px;
}

.custom-input-number :deep(.el-input-number__decrease:hover),
.custom-input-number :deep(.el-input-number__increase:hover) {
  color: #ff69b4; /* hover 变成粉色 */
  box-shadow: 0 0 0 1px #ff69b4; /* 添加粉色的外阴影 */
}

.add-to-cart {
  background-color: #000;
  color: #fff;
  padding: 13px 200px;
  border-radius: 8px;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 5px;
  margin-top: 20px;
  margin-left:20px;
}

.add-to-cart i {
  margin-right: 5px;
}

.details h3 {
  margin-top: 20px;
}
.footer-bottom{
  margin-top: 80px;
  margin-bottom: 20px;
}
</style>
