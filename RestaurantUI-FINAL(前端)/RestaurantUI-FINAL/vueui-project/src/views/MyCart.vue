<template>
  <div class="app-container">
    <nav class="navbar">
      <div class="container">
        <div class="nav-content">
          <div class="nav-brand">
            <img class="logo" src="../assets/logo.png" alt="Logo">
          </div>
          <div class="nav-links">
            <router-link to="/" class="nav-link active">首页</router-link>
            <router-link to="/menu" class="nav-link">菜单</router-link>
            <router-link to="/cart" class="nav-link">购物车</router-link>
             <a href="http://172.20.84.75:8081/UserProfile" class="nav-link">个人中心</a>
          </div>
        </div>
      </div>
    </nav>

    <main class="main-content">
      <div class="grid-container">
        <div class="main-column">
          <div class="shopping-cart">
            <h2 class="cart-title">购物车</h2>
            <div class="continue-shopping">
              <router-link to="/menu" class="continue-shopping-button">继续购物</router-link>
            </div>
            <div class="cart-items">
              <div class="select-all">
                <input type="checkbox" @change="toggleSelectAll" :checked="isAllSelected" />
                <span class="select-label">全选</span>
              </div>
              <div class="cart-items" id="myComponent">
                <div v-for="item in cartItems" :key="item.cartitemid" class="cart-item">
                  <input type="checkbox" v-model="item.selected" />
                  <img :src="item.img" alt="商品" class="item-img" />
                  <div class="item-details">
                    <h3 class="item-name">{{ item.name }}</h3>
                    <p class="item-price">￥{{ item.price.toFixed(2) }}</p>
                    <p class="item-id">菜品 ID: {{ item.dishid }}</p>
                    <div class="quantity-control">
                      <button @click="updateQuantity(item.cartitemid, -1)" class="quantity-button round-button"><i
                          :class="['fas', 'fa-minus']"></i></button>
                      <input type="number" v-model="item.quantity" min="1" class="quantity-input" />
                      <button @click="updateQuantity(item.cartitemid, 1)" class="quantity-button round-button"><i
                          :class="['fas', 'fa-plus']"></i></button>
                    </div>
                  </div>
                  <div class="item-total">
                    <p class="total-price">￥{{ (item.price * item.quantity).toFixed(2) }}</p>
                    <button @click="removeItem(item.cartitemid)" class="remove-button"><i
                        class="fas fa-trash"></i></button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="shipping-info">
            <h2 class="info-title">收货信息</h2>
            <div class="info-fields">
              <div>
                <label class="field-label">收货人</label>
                <input type="text" v-model="receiver.name" class="field-input" />
              </div>
              <div>
                <label class="field-label">联系电话</label>
                <input type="tel" v-model="receiver.phone" class="field-input" />
              </div>
              <div>
                <label class="field-label">收货地址</label>
                <input type="text" v-model="receiver.address" class="field-input" />
              </div>
            </div>
          </div>
        </div>

        <div class="summary-column">
          <div class="order-summary">
            <h2 class="summary-title">订单总结</h2>
            <div class="summary-details">
              <div class="summary-item">
                <span class="summary-label">商品总额</span>
                <span class="summary-value">￥{{ totalPrice.toFixed(2) }}</span>
              </div>
              <div class="summary-item">
                <span class="summary-label">配送费</span>
                <span class="summary-value">￥5.00</span>
              </div>
              <div class="summary-total">
                <span class="total-label">合计</span>
                <span class="total-value">￥{{ (totalPrice + 5).toFixed(2) }}</span>
              </div>
              <button class="pay-button" @click="makePayment">去支付</button>
            </div>
          </div>
        </div>
      </div>
    </main>

    <footer class="footer">
      <div class="footer-container">
        <div class="footer-content">
          <div class="social-links">
            <a href="#" class="social-icon"><i class="fab fa-weixin"></i></a>
            <a href="#" class="social-icon"><i class="fab fa-weibo"></i></a>
          </div>
          <p class="footer-text">&copy; 2024 美味点餐系统. All rights reserved.</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';

export default {
  name: 'ShoppingCart',  // 多单词组件名，避免与HTML标签冲突
  setup() {
    const totalmoney = ref(0); // 总支付金额
    const userid = ref(1); // 用户ID
    const cartItems = ref([]);
    const receiver = ref({
      name: '',
      phone: '',
      address: '',
    });

    const isAllSelected = computed(() => {
      return Array.isArray(cartItems.value) && cartItems.value.every(item => item.selected);
    });

    const totalPrice = computed(() => {
      return cartItems.value.filter(item => item.selected).reduce((total, item) => total + item.price * item.quantity, 0);
    });

    const toggleSelectAll = () => {
      cartItems.value.forEach(item => {
        item.selected = !isAllSelected.value;
      });
    };

    const updateQuantity = (itemId, delta) => {
      const item = cartItems.value.find(item => item.cartitemid === itemId);
      if (item) {
        item.quantity = Math.max(item.quantity + delta, 1);
      } else {
        console.error('未找到要更新数量的商品项。');
      }
    };

    const removeItem = (cartitemid) => {
      const index = cartItems.value.findIndex(item => item.cartitemid === cartitemid);
      if (index !== -1) {
        axios.delete(`http://43m93z.natappfree.cc/cart/items/${cartitemid}`)
          .then(() => {
            cartItems.value.splice(index, 1);
          })
          .catch(error => {
            console.error('删除商品失败:', error);
          });
      } else {
        console.log('未找到要删除的商品项。');
      }
    };

    const getCartItemsByUserId = () => {
      const userId = 1; // 这里应该根据实际情况获取用户ID
      axios.get('http://43m93z.natappfree.cc/cart/items', { params: { userid: userId } })
        .then(response => {
          if (response.data.code === 1) {
            cartItems.value = response.data.data.map(item => ({
              cartitemid: item.cartitemid,
              dishid: item.dishid,
              name: '', // 初始化name为空
              price: 0, // 初始化price为0
              img: '', // 初始化图片为空
              quantity: item.quantity || 1, // 根据实际数据项，设置初始数量
              selected: false, // 默认未选中
            }));
            fetchDishDetails();
          }
        })
        .catch(error => {
          console.error('获取购物车数据失败:', error);
        });
    };

    const fetchDishDetails = () => {
      cartItems.value.forEach(item => {
        axios.get(`http://43m93z.natappfree.cc/dishes/${item.dishid}`)
          .then(response => {
            const dish = response.data.data;
            item.name = dish.name;
            item.price = dish.price;
            item.img = dish.img;
          })
          .catch(error => {
            console.error('获取菜品数据失败:', error);
          });
      });
    };

     // 支付处理方法
    const makePayment = () => {
      if (totalPrice.value === 0) {
        alert('请至少选择一个商品进行支付！');
        return;
      }

      totalmoney.value = totalPrice.value + 5; // 获取购物车总金额并加上配送费
      // 使用window.open跳转到支付页面
      window.open(`http://43m93z.natappfree.cc/alipay/pay?totalmoney=${totalmoney.value}&userid=${userid.value}`);

      // 清空购物车页面（假设是清除某个特定元素）
      document.getElementById('myComponent').innerHTML = '';
    };
    onMounted(() => {
      getCartItemsByUserId();
    });

    return {
      cartItems,
      receiver,
      isAllSelected,
      totalPrice,
      toggleSelectAll,
      updateQuantity,
      removeItem,
      makePayment,
    };
  }
};
</script>



<style scoped>
.app-container {
  background-color: #F9FAFB;
  font-family: 'Noto Sans SC', sans-serif;
}

.navbar {
  background-color: #FFFFFF;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}

.container {
  max-width: 80rem;
  /* Max width */
  margin: 0 auto;
  /* Center alignment */
  padding-left: 1rem;
  padding-right: 1rem;
}

.nav-content {
  display: flex;
  justify-content: space-between;
  /* Space between items */
  align-items: center;
  /* Vertical alignment */
  height: 64px;
  /* Set height */
}

.nav-brand .logo {
  height: 70px;
  /* Logo height */
}

.nav-links {
  display: flex;
  /* Flexbox for nav links */
}

.nav-link {
  padding: 0.25rem 0.5rem;
  /* Padding */
  margin-left: 1rem;
  /* Left margin */
  color: #6B7280;
  /* Link color */
}

.nav-link.active {
  border-bottom: 2px solid #1D4ED8;
  /* Active link underline */
  color: #111827;
  /* Active link color */
}

.main-content {
  max-width: 80rem;
  margin: 0 auto;
  padding: 2rem;
  display: flex;
  /* 使用flex布局 */
}

.grid-container {
  display: flex;
  /* Flexbox布局 */
  flex: 1;
  /* 主列占据剩余的空间 */
}

.main-column {
  flex: 3;
  /* 主列占据三分之二空间 */
  margin-right: 2rem;
  /* 右侧留出空间给订单摘要 */
}

.shopping-cart {
  background-color: #FFFFFF;
  /* White background */
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
  /* Soft shadow */
  border-radius: 0.5rem;
  /* Rounded corners */
  padding: 1.5rem;
  /* Padding */
}

.cart-title {
  font-size: 1.125rem;
  /* Title font size */
  font-weight: 500;
  /* Medium weight */
  color: #111827;
  /* Dark text */
}

.continue-shopping {
  position: absolute;
  top: -30px;
  right: 190px;
  margin: 11rem;
}

.continue-shopping-button {
  color: rgb(8, 7, 7);
  padding: 0.5rem 1rem;
  border-radius: 0.375rem;
  text-decoration: none;
  transition: background-color 0.3s;
}

.continue-shopping-button:hover {
  color: rgb(165, 164, 164);
}

.cart-items {
  border-top: 1px solid #E5E7EB;
  /* Top border */
}

.select-all {
  display: flex;
  /* Flexbox for select all */
  align-items: center;
  /* Center alignment */
  padding: 1rem;
  /* Padding */
}

.select-label {
  margin-left: 0.5rem;
  /* Margin */
  color: #6B7280;
  /* Gray text */
}

.items-list {
  border-top: 1px solid #E5E7EB;
  /* Border for items list */
}

.cart-item {
  display: flex;
  /* Flexbox for cart item */
  align-items: center;
  /* Center alignment */
  padding: 1rem;
  /* Padding */
  border-bottom: 1px solid #E5E7EB;
  /* Item separator */
}

.item-img {
  margin-left: 1rem;
  /* Image margin */
  height: 80px;
  /* Image height */
  width: 80px;
  /* Image width */
  object-fit: cover;
  /* Cover the space */
  border-radius: 0.375rem;
  /* Rounded corners */
}

.item-details {
  flex: 1;
  /* Take remaining space */
  margin-left: 1rem;
  /* Left margin */
}

.item-name {
  font-size: 0.875rem;
  /* Item name font size */
  font-weight: 500;
  /* Medium weight */
  color: #111827;
  /* Dark text */
}

.item-price {
  margin-top: 0.25rem;
  /* Margin top */
  font-size: 0.875rem;
  /* Price font size */
  color: #6B7280;
  /* Gray text */
}

.quantity-control {
  display: flex;
  /* Flexbox for quantity control */
  align-items: center;
  /* Center alignment */
  margin-top: 0.5rem;
  /* Top margin */
}

.quantity-button {
  color: #00050a;
  /* Button color */
}

.quantity-input {
  margin: 0 0.5rem;
  /* Margin */
  width: 3rem;
  /* Fixed width */
  text-align: center;
  /* Center text */
  border: 1px solid #D1D5DB;
  /* Border */
  border-radius: 0.375rem;
  /* Rounded corners */
}

.item-total {
  display: flex;
  margin-left: 1rem;
  /* Margin */
}

.total-price {
  font-size: 0.875rem;
  /* Total price font size */
  font-weight: 500;
  /* Medium weight */
  color: #111827;
  /* Dark text */
}

.remove-button {
  margin-top: 0.5rem;
  /* Top margin */
  color: #000d0d;
  /* Gray color */
  cursor: pointer;
  /* Pointer cursor */
}

.shipping-info {
  background-color: #FFFFFF;
  /* White background */
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
  /* Soft shadow */
  border-radius: 0.5rem;
  /* Rounded corners */
  padding: 1.5rem;
  /* Padding */
  margin-top: 2rem;
  /* Top margin */
}

.info-title {
  font-size: 1.125rem;
  /* Title font size */
  font-weight: 500;
  /* Medium weight */
  color: #111827;
  /* Dark text */
  margin-bottom: 1.5rem;
  /* Margin bottom */
}

.info-fields .field-label {
  display: block;
  /* Block display */
  font-size: 0.875rem;
  /* Font size */
  font-weight: 500;
  /* Medium weight */
  color: #374151;
  /* Dark gray */
}

.field-input {
  margin-top: 0.25rem;
  /* Top margin */
  width: 100%;
  /* Full width */
  height: 2rem;
  /* Fixed height */
  border: 1px solid #D1D5DB;
  /* Border */
  border-radius: 0.375rem;
  /* Rounded corners */
}

.summary-column {
  flex: 1;
  /* 订单总结占据三分之一空间 */
  position: sticky;
  /* 固定位置 */
  top: 2rem;
  /* 向下偏移一些，以避免与顶部导航栏重叠 */
  max-height: calc(100vh - 8rem);
  /* 最大高度设置为视口高度减去导航和其他元素的高度 */
  overflow-y: auto;
  /* 如果内容超出高度，则出现滚动条 */
}

.order-summary {
  background-color: #FFFFFF;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
  border-radius: 0.5rem;
  padding: 1.5rem;
}

.summary-title {
  font-size: 1.125rem;
  font-weight: 500;
  color: #111827;
  margin-bottom: 1.5rem;
}

.summary-details {
  display: flex;
  /* Flexbox for summary */
  flex-direction: column;
  /* Column layout */
}

.summary-item {
  display: flex;
  /* Flexbox for summary item */
  justify-content: space-between;
  /* Space between */
}

.summary-label {
  color: #6B7280;
  /* Gray text */
}

.summary-value {
  font-weight: 500;
  /* Bold text */
}

.summary-total {
  display: flex;
  border-top: 1px solid #E5E7EB;
  /* Total border */
  padding-top: 1rem;
  /* Padding top */
}

.total-label {
  font-size: 1.125rem;
  /* Total label size */
  font-weight: 500;
  /* Medium weight */
}

.total-value {
  padding-left: 7.9rem;
  font-size: 1.125rem;
  /* Total value size */
  font-weight: 500;
  /* Medium weight */
  color: #000103;
  /* Custom color */
}

.pay-button {
  width: 100%;
  /* Full width */
  background-color: #00030a;
  /* Custom button color */
  color: white;
  /* White text */
  padding: 0.5rem 1rem;
  /* Padding */
  border-radius: 0.5rem;
  /* Rounded corners */
  cursor: pointer;
  /* Pointer cursor */
  transition: background-color 0.3s;
  /* Smooth transition */
}

.pay-button:hover {
  background-color: #606062;
  /* Darker on hover */
}

.footer {
  background-color: #FFFFFF;
  /* White background */
}

.footer-container {
  max-width: 80rem;
  /* Max width */
  margin: 0 auto;
  /* Center alignment */
  padding: 3rem 1rem;
  /* Padding */
}

.footer-content {
  display: flex;
  /* Flexbox for footer */
  justify-content: space-between;
  /* Space between items */
  align-items: center;
  /* Center alignment */
}

.social-links {
  display: flex;
  /* Flexbox for social links */
  gap: 1.5rem;
  /* Spacing */
}

.social-icon {
  color: #9CA3AF;
  /* Gray color */
  transition: color 0.2s;
  /* Transition */
}

.social-icon:hover {
  color: #6B7280;
  /* Darker on hover */
}

.footer-text {
  margin-top: 2rem;
  /* Margin top */
  font-size: 0.875rem;
  /* Base font size */
  color: #9CA3AF;
  /* Gray text */
}
</style>