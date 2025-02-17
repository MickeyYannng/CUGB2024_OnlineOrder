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

  <div class="container">
       <!-- 菜单标签栏 -->
         <ul class="tab-menu">
          <li
            v-for="(tab, index) in cuisineTabs" 
            :key="index"
            :class="['tab-item', { active: selectedCuisine === tab.name }]"
            @click="TabClick(tab.name)"  
          >
            {{ tab.label }} <!-- 显示标签名 -->
          </li>
        </ul>
        <!-- class="custom-tabpanes" -->
          <div class="main">
          <div
          v-for="dish in dishes"
          :key="dish.dishid"
          class="bg-custom"
          >
              <div class="aspect">
              <img :src="dish.img"
              :alt="dish.name" 
              @click="goToDetail(dish.dishid)"
                class=""/>
              </div>
              <div class="info-container">
              <h3 @click="goToDetail(dish.dishid)" class="dish-name">
                {{ dish.name }}
              </h3>

              <div class="control-bar">
                <p class="dish-price">¥{{ dish.price }}</p>
                <button class="cart-butt" @click="addToCart(dish.dishid)">
                  <i class="fas fa-shopping-cart"></i>
                </button>
              </div>

              </div>
          </div>
        </div>

   <div class="pagination">
      <nav>
         <img src="../assets/left.png" alt="Left Image" class="pagination-imagel" />
        <!-- 上一页 -->
        <button
          :disabled="currentPage === 1"
          @click="goToPage(currentPage - 1)"
          class="pagination-button"
        >
          &lt; 上一页
        </button>
        <!-- 页码 -->
        <button
          v-for="page in totalPages"
          :key="page"
          :class="['pagination-button', {'active-page': page === currentPage}]"
          @click="goToPage(page)"
        >
          {{ page }}
        </button>
        <!-- 下一页 -->
        <button
          :disabled="currentPage === totalPages"
          @click="goToPage(currentPage + 1)"
          class="pagination-button"
        >
          下一页 &gt;
        </button>
         <img src="../assets/right.png" alt="Right Image" class="pagination-imager" />
      </nav>
    </div>
    <router-link to="/cart">
        <div class="cart">
            <button>
              <i class="fas fa-shopping-cart"></i>查看购物车
            </button>
        </div>
    </router-link>
      <div class="bottom">
      </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const searchQuery = ref('');
const router = useRouter();
const cuisineTabs = ref([
  { label: '全部菜品', name: 'all' },
  { label: '山西菜', name: 'shanxi' },
  { label: '四川菜', name: 'sichuan' },
  { label: '内蒙菜', name: 'inner-mongolia' },
  { label: '东北菜', name: 'northeast' },
]);
// 当前页数据
const dishes = ref([]);
const currentPage = ref(1); // 当前页码
const totalPages = ref(0); // 总页数
const pageSize = ref(12); // 每页条数
const selectedCuisine = ref('all');

const handleSearch = () => {
  console.log('Searching for:', searchQuery.value);
  // 根据搜索内容过滤菜品数据
  if (searchQuery.value.trim()) {
    dishes.value = dishes.value.filter(dish =>
      dish.name.toLowerCase().includes(searchQuery.value.trim().toLowerCase())
    );
  } else {
    // 如果搜索框为空，则恢复为初始状态
    loadDishes();
  }
};

const goToAdminControl = () => {
  console.log('goToAdminControl clicked'); // 输出调试信息
  try {
    window.location.href = "http://172.20.84.75:8081/UserProfile";
  } catch (error) {
    console.error('Navigation error:', error); // 如果跳转失败，输出错误信息
  }
};

// 载入菜品数据
const loadDishes = async () => {
  try {
    const response = await axios.get(
      `http://43m93z.natappfree.cc/dishes/bypage`, {
      params: {
        page: currentPage.value,
        pagesize: pageSize.value,
      },
    });
    const data = response.data;
    if (data.code === 1) {
      dishes.value = data.data.list.map((dish) => ({
        ...dish,
        quantity: 0, // 初始化数量为 0
      }));
      totalPages.value = Math.ceil(data.data.total / pageSize.value);
    } else {
      console.error("Failed to fetch dishes:", data.msg);
    }
  } catch (error) {
    console.error("Error fetching dishes:", error);
  }
};

// 根据菜系筛选菜品
const filterDishes = () => {
  const cuisine = (selectedCuisine.value || 'all').trim().toLowerCase(); // 确保有默认值
  console.log("Selected cuisine:", cuisine);
  switch (cuisine) {
    case "shanxi":
      currentPage.value=1;
      loadDishes();
      console.log("Shanxi dishes...");
      break;
    case "sichuan":
       currentPage.value=2;
       loadDishes();
      console.log("Sichuan dishes...");
      break;
    case "inner-mongolia":
       currentPage.value=3;
       loadDishes();
      console.log("Inner Mongolia dishes...");
      break;
    case "northeast":
       currentPage.value=4;
       loadDishes();
      console.log("Northeast dishes...");
      break;
    default:
       currentPage.value=1;
      console.log("All dishes...");
  }
};

// 点击事件：更新选中菜系
const TabClick = (name) => {
  selectedCuisine.value = name; // 更新选中状态
  console.log("Tab clicked, selected cuisine:", selectedCuisine.value);
  filterDishes(); // 更新菜品筛选逻辑
};

// 页面切换
const goToPage = (page) => {
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page;
    loadDishes();
  }
   window.scrollTo(0, 0);
};

// 页面跳转到详情
const goToDetail = (dishId) => {
  router.push(`/detail/${dishId}`);
};

// 发送请求，添加菜品到购物车
const addToCart = async (dishId) => {
  try {
    console.log('Adding to cart, dishid:', dishId); // 检查dishId
    const params = {
      userid: 1, 
      dishid: dishId, // 使用传递过来的 dishId
    };
    const response = await axios.post('http://43m93z.natappfree.cc/cart/add', null, {
      params,
    });
    if (response.data.code === 1) {
      alert('已将该菜品加入购物车！');
    } else {
      alert('加入购物车失败，请重试');
    }
  } catch (error) {
    console.error('Error adding to cart:', error);
    alert('请求失败，请重试');
  }
};

function goToHome(){
 router.push('/');
}
onMounted(async () => {
  await loadDishes(); // 加载菜品
  filterDishes(); // 根据默认值筛选
});
</script>

<style scoped>
.container{
  display: flex;
  flex-direction: column;
}
.tab-menu {
  display: flex;
  border-bottom: 1px solid #ddd;
  height:40px;
  margin-bottom: 15px;
  padding: 0;
  list-style: none;
}

.tab-item {
  flex: 1;
  text-align: center;
  padding: 10px 0;
  cursor: pointer;
  font-size: 14px;
  color: #666;
  transition: color 0.3s, background 0.3s;
}

.tab-item:hover {
  background: #f5f5f5;
}

.tab-item.active {
  color: #ff80ab;
  border-bottom: 2px solid #ff80ab;
  font-weight: bold;
}

.tab-content {
  padding: 20px;
  font-size: 16px;
  color: #333;
}



.main{
  background: #ffffff30;
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 16px;
}
.bg-custom {
    overflow: hidden;
    margin-left: 25px;
    width:350px;
    background-color: #ffffff; /* Example custom color */
    /* padding: 16px; */
    border-radius: 8px;
    position: relative;
}
/* 价格样式 */
.dish-price {
    font-size: 20px;
    color: #444;
    font-weight: bold;
    margin-left: 10px;
}

.dish-name{
    margin-left: 10px;
    font-size: 18px;
    font-weight: bold;
    cursor: pointer;
}
.control-bar{
    display: flex;
    justify-content: space-between; /* 价格在左，加减在右 */
    align-items: flex-end;
    margin-top: 10px;
    position: relative;
}
.cart-butt{
  background-color: pink;
  border: none;
  padding: 10px;
  border-radius: 8px;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  color: rgb(0, 0, 0); /* 设置图标为白色 */
}

.aspect img {
  border-radius: 7px;
  width: 350px;
  height: 250px; 
  cursor: pointer;
  object-fit: cover; /* 保持图片内容适配框 */
}


.pagination-imagel {
    width: 110px; /* 可根据需要调整图片宽度 */
}
.pagination-imager {
    width: 150px; /* 可根据需要调整图片宽度 */
}

/* 分页 */
.pagination {
  display: flex;
  justify-content: center;
  gap: 8px;
  margin: 70px 0;
}

.pagination-button {
  border: 1px solid #ddd;
  padding: 8px 16px;
  background-color: #fff;
  cursor: pointer;
  border-radius: 5px;
  font-size: 14px;
  color: #333;
}

.pagination-button:hover {
  background-color: #f1f1f1;
}

.pagination-button[disabled] {
  cursor: not-allowed;
  opacity: 0.5;
}

.active-page {
  background-color: #000;
  color: #fff;
  font-weight: bold;
}
/* 购物车 */
.cart{
  position: fixed;
  height: 5%;
  width: 100%;
  background-color: #fff; 
  bottom: 0;
}

.cart button{
  position: fixed;
  background: #000;
  color: #fff;
  height: 40px;
  width: 96%;
  bottom: 10px;
  left: 20px;
  right: 20px;
  border-radius: 8px;
  cursor: pointer;
}
.bottom{
  height: 30px;
}
</style>
