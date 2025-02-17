<template>
    <div class="bg-gray-100 font-['Noto_Sans_SC']">
        <nav class="bg-white shadow-lg fixed w-full z-10">
    <div class="max-w-8xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex items-center justify-between h-16">
            <div class="flex items-center">
                <img class="h-10 w-auto" src="@/assets/logo.png" alt="故乡食间">
                <h2 class="ml-4 text-2xl font-bold text-gray-900">故乡食间</h2>
            </div>
            <!-- 添加退出按钮 -->
            <div class="flex items-center">
                <button class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded" onclick="window.location.href='http://172.25.115.13:8080/'">
                    退出
                </button>
            </div>
        </div>
    </div>
</nav>
        <div class="max-w-8xl mx-auto px-4 sm:px-6 lg:px-8 py-24">
            <div class="bg-white shadow-md rounded-lg overflow-hidden mb-8">
                <div class="p-8">
    <div class="flex items-center justify-between">
        <div class="flex items-center space-x-6">
            <img class="h-24 w-24 rounded-full object-cover border-2 border-gray-300 shadow" src="@/assets/person.png" />
            <div>
                <h2 class="text-3xl font-bold text-gray-900">{{ user.name }}</h2>
                <div class="flex items-center mt-2">
                    <span class="bg-custom text-blue-500 text-sm px-3 py-1 rounded-full">{{ user.membership }}</span>
                    <span class="ml-4 text-gray-600">积分：{{ user.points }}</span>
                </div>
            </div>
        </div>
        <button class="!rounded-button border border-black bg-white text-blue-500 px-4 py-2 hover:bg-custom/90 hover:text-white">
            <i class="fas fa-key mr-2"></i>修改密码
        </button>
    </div>
</div>
            </div>

            <div class="grid grid-cols-4 gap-8 mb-8">
                <a href="#personal" class="flex flex-col items-center justify-center bg-white p-8 rounded-lg shadow-lg hover:shadow-2xl transition duration-300">
                    <i class="fas fa-user text-4xl text-custom mb-4"></i>
                    <span class="text-gray-900 text-lg">个人信息</span>
                </a>
                <a href="#reviews" class="flex flex-col items-center justify-center bg-white p-8 rounded-lg shadow-lg hover:shadow-2xl transition duration-300">
                    <i class="fas fa-star text-4xl text-custom mb-4"></i>
                    <span class="text-gray-900 text-lg">我的评价</span>
                </a>
                <a href="#orders" class="flex flex-col items-center justify-center bg-white p-8 rounded-lg shadow-lg hover:shadow-2xl transition duration-300">
                    <i class="fas fa-file-invoice text-4xl text-custom mb-4"></i>
                    <span class="text-gray-900 text-lg">历史订单</span>
                </a>
                <a href="javascript:void(0);" class="flex flex-col items-center justify-center bg-white p-8 rounded-lg shadow-lg hover:shadow-2xl transition duration-300" @click="goToCart">
  <i class="fas fa-shopping-cart text-4xl text-custom mb-4"></i>
  <span class="text-gray-900 text-lg">我的购物车</span>
</a>
            </div>

            <div id="personal" class="bg-white shadow-md rounded-lg p-8 mb-8">
                <h3 class="text-2xl font-medium text-gray-900 mb-8">个人信息</h3>
                <div class="grid grid-cols-2 gap-8">
                    <div>
                        <label class="block text-lg font-medium text-gray-700 mb-2">昵称</label>
                        <input type="text" v-model="user.name" class="w-full border-gray-300 rounded-lg shadow-sm focus:ring-custom focus:border-custom p-2 text-lg" />
                    </div>
                    <div>
                        <label class="block text-lg font-medium text-gray-700 mb-2">手机号</label>
                        <input type="tel" v-model="user.phone" class="w-full border-gray-300 rounded-lg shadow-sm focus:ring-custom focus:border-custom p-2 text-lg" />
                    </div>
                    <div>
                        <label class="block text-lg font-medium text-gray-700 mb-2">邮箱</label>
                        <input type="email" v-model="user.email" class="w-full border-gray-300 rounded-lg shadow-sm focus:ring-custom focus:border-custom p-2 text-lg" />
                    </div>
                    <div>
                        <label class="block text-lg font-medium text-gray-700 mb-2">收货地址</label>
                        <input type="text" v-model="user.address" class="w-full border-gray-300 rounded-lg shadow-sm focus:ring-custom focus:border-custom p-2 text-lg" />
                    </div>
                </div>
                <div class="mt-8 flex justify-end">
    <button class="!rounded-button border border-custom bg-custom text-blue px-6 py-3 hover:bg-custom/90 transition duration-300 hover:text-white" @click="saveUserInfo">保存修改</button>
</div>
            </div>


            <div id="reviews" class="bg-white shadow-md rounded-lg p-8 mb-8">
                <h3 class="text-2xl font-medium text-gray-900 mb-8">我的评价</h3>
                <div class="space-y-8">
                    <div v-for="review in reviews" :key="review.id" class="border-b-2 pb-8">
                        <div class="flex items-center justify-between">
                            <div class="flex items-center">
                                <img class="h-20 w-20 object-cover rounded-lg shadow-md" :src="review.image"
                                    :alt="review.productName" />
                                <div class="ml-6">
                                    <h4 class="text-xl font-medium text-gray-900">{{ review.productName }}</h4>
                                    <div class="flex items-center mt-2">
                                        <i v-for="star in review.stars" :key="star" class="fas fa-star text-yellow-400 text-lg mr-1"></i>
                                    </div>
                                </div>
                            </div>
                            <span class="text-gray-600 text-lg">{{ review.date }}</span>
                        </div>
                        <p class="text-gray-700 mt-4 text-lg">{{ review.comment }}</p>
                    </div>
                </div>
            </div>

            <div id="orders" class="bg-white shadow-md rounded-lg p-8">
                <h3 class="text-2xl font-medium text-gray-900 mb-8">历史订单</h3>
                <div class="space-y-8">
                    <div v-for="order in orders" :key="order.id" class="border rounded-lg p-6 shadow-md">
                        <div class="flex items-center justify-between mb-6">
                            <span class="text-gray-600 text-lg">订单编号：{{ order.orderNumber }}</span>
                            <span class="text-custom text-lg">{{ order.status }}</span>
                        </div>
                        <div class="flex items-center border-b-2 pb-6">
                            <img class="h-24 w-24 object-cover rounded-lg shadow-md" :src="order.image" :alt="order.productName" />
                            <div class="ml-6 flex-1">
                                <h4 class="text-xl font-medium text-gray-900">{{ order.productName }}</h4>
                                <p class="text-gray-600 mt-2 text-lg">数量：{{ order.quantity }}</p>
                            </div>
                            <span class="text-custom font-medium text-xl">¥{{ order.price.toFixed(2) }}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <footer class="bg-gray-800 text-white mt-24">
            <div class="max-w-8xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
                <div class="text-center">
                    <p class="text-lg">© 2024 故乡食间. 版权所有</p>
                </div>
            </div>
        </footer>
    </div>
</template>

<script>
import { reactive, onMounted } from "vue";
import axios from "axios";

export default {
  setup() {
    const user = reactive({
      userid: "1",
      avatar: "@/assets/person.png",
      name: "",
      membership: "黄金会员",
      points: 2360,
      phone: "",
      email: "",
      address: "",
    });

    const reviews = reactive([
      {
        id: 1,
        image: "@/assets/大千干烧鱼.png",
        productName: "大千干烧鱼",
        stars: 5,
        date: "2024-01-15",
        comment: "味道非常好，皮薄馅大，和家乡的味道一模一样！",
      },
      {
        id: 2,
        image: "@/assets/大炸羊.png",
        productName: "大炸羊",
        stars: 4,
        date: "2024-01-10",
        comment: "月饼口感细腻，馅料实在，老人们都很喜欢。",
      },
    ]);

    const orders = reactive([
      {
        id: 1,
        orderNumber: "2024011501",
        status: "已完成",
        image: "@/assets/扒驼掌.png",
        productName: "扒驼掌",
        quantity: "1份",
        price: 136.0,
      },
      {
        id: 2,
        orderNumber: "2024011001",
        status: "已完成",
        image: "@/assets/夫妻肺片.png",
        productName: "夫妻肺片",
        quantity: "1份",
        price: 128.0,
      },
    ]);

    const fetchUserInfo = async () => {
      try {
        const response = await axios.get("http://43m93z.natappfree.cc/users/1");
        if (response.data.code === 1) {
          const userData = response.data.data;
          Object.assign(user, {
            userid: userData.userid.toString(),
            avatar: "@/assets/person.png",
            name: userData.username,
            membership: "黄金会员",
            points: 2360,
            phone: userData.phone,
            email: userData.email,
            address: userData.address,
          });
        } else {
          console.error("获取个人信息出错：", response.data.msg);
          alert("获取个人信息失败：" + response.data.msg);
        }
      } catch (error) {
        console.error("获取个人信息出错：", error);
        alert("获取个人信息时发生错误，请稍后再试。");
      }
    };

    const saveUserInfo = async () => {
      try {
        const userData = {
          userid: user.userid,
          username: user.name,
          phone: user.phone,
          email: user.email,
          address: user.address,
        };
        const response = await axios.put(
          "http://43m93z.natappfree.cc/users",
          userData
        );
        if (response.data.code === 1) {
          alert("个人信息更新成功！");
        } else {
          alert("更新失败：" + response.data.msg);
        }
      } catch (error) {
        console.error("更新个人信息出错：", error);
        alert("更新个人信息时发生错误，请稍后再试。");
      }
    };

    const logout = () => {
      window.location.href = "http://172.25.115.13:8080/";
    };

    onMounted(fetchUserInfo);

    return {
      user,
      reviews,
      orders,
      logout,
      fetchUserInfo,
      saveUserInfo,
    };
  },
};
</script>


<style>
/* 自定义样式 */
.text-custom {
    color: #FF6347; /* 橙红色 */
}
.bg-custom {
    background-color: #FF6347; /* 橙红色 */
}

.border-custom {
    border-color: #FF6347; /* 橙红色 */
}

.rounded-button {
    border-radius: 10px;
}

/* 响应式设计 */
@media (max-width: 768px) {
    .grid-cols-4 {
        grid-template-columns: 1fr;
    }
    .ml-6, .ml-4, .mr-4, .mr-8 {
        margin: 0;
    }
}

@media (max-width: 1024px) {
    .grid-cols-4 {
        grid-template-columns: repeat(2, 1fr);
    }
}

@media (max-width: 1280px) {
    .grid-cols-4 {
        grid-template-columns: repeat(3, 1fr);
    }
}
</style>