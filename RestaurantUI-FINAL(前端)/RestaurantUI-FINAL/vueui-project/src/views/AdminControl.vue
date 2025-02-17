<template>
  <div class="min-h-screen flex">
    <!-- 导航栏 -->
    <nav class="fixed top-0 z-50 w-full bg-white border-b border-gray-200">
      <div class="px-3 py-3 lg:px-5 lg:pl-3">
        <div class="flex items-center justify-between">
          <div class="flex items-center justify-start">
            <button type="button"
                    class="inline-flex items-center p-2 text-sm text-gray-500 rounded-lg hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200"
                    aria-controls="logo-sidebar"
                    @click="toggleSidebar">
              <i class="fas fa-bars"></i>
            </button>
            <a href="#" class="flex ml-2 md:mr-24">
              <img src="@/assets/logo.png" class="h-8 mr-3"
                   alt="Logo" />
              <span class="self-center text-xl font-semibold sm:text-2xl whitespace-nowrap">故乡食间</span>
            </a>
          </div>
          <div class="flex items-center">
            <div class="flex items-center ml-3">
              <div class="flex items-center gap-2">
                <img class="w-8 h-8 rounded-full"
                     src="@/assets/person.png"
                     alt="管理员头像" />
                <div class="text-sm">
                  <div class="font-medium text-gray-900">张管理员</div>
                  <div class="text-gray-500 text-xs">系统管理员</div>
                </div>
                <button type="button"
                        class="!rounded-button ml-4 bg-custom text-white px-4 py-2 text-sm font-medium hover:bg-custom/90"
                        @click="logout">
                  <i class="fas fa-sign-out-alt mr-2"></i>退出登录
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </nav>

    <!-- 侧边栏 -->
    <aside id="logo-sidebar"
           class="fixed top-0 left-0 z-40 w-64 h-screen pt-20 transition-transform bg-white border-r border-gray-200"
           :class="{'-translate-x-full': !isSidebarOpen}">
      <div class="h-full px-3 pb-4 overflow-y-auto bg-white">
        <ul class="space-y-2 font-medium">
          <li>
            <a href="#"
               class="flex items-center p-3 text-custom rounded-lg bg-custom/10"
               :class="{'bg-custom text-white': isUsersActive, 'bg-custom/10 text-custom': !isUsersActive}"
               @click="showUsers">
              <i class="fas fa-users w-5 h-5 transition duration-75"></i>
              <span class="ml-3">用户管理</span>
            </a>
          </li>
          <li>
            <a href="#"
               class="flex items-center p-3 text-custom rounded-lg hover:bg-gray-100 cursor-pointer"
               :class="{'bg-custom text-white': isDishesActive, 'bg-white text-gray-900': !isDishesActive}"
               @click="showDishes">
              <i class="fas fa-utensils w-5 h-5 text-gray-500 transition duration-75"></i>
              <span class="ml-3">菜品管理</span>
            </a>
          </li>
        </ul>
      </div>
    </aside>

    <!-- 主内容区 -->
    <div class="p-4 sm:ml-64 pt-20 w-full">
      <div class="mb-6 flex items-center justify-between">
        <!-- 搜索类型选择器和搜索栏 -->
        <div class="flex items-center gap-4">
          <select v-model="searchType" class="bg-white border border-gray-300 text-gray-900 text-sm !rounded-button w-32 p-2">
            <option v-for="column in columns" :value="column" :key="column">{{ column }}</option>
          </select>
          <input type="text"
                 class="bg-white border border-gray-300 text-gray-900 text-sm !rounded-button block w-64 p-2.5 pl-10"
                 placeholder="搜索..."
                 v-model="searchQuery" />
        </div>

<!-- 编辑按钮 -->
<button type="button"
        class="!rounded-button bg-custom text-white px-4 py-2 flex items-center gap-2 hover:bg-custom/90"
        @click="toggleEditMode"
        v-if="!isEditMode">
  <i class="fas fa-edit"></i>
  <span>编辑</span>
</button>
<!-- 编辑模式下的按钮 -->
<div v-if="isEditMode" class="flex items-center gap-4">
  <button type="button"
          class="!rounded-button bg-green-600 text-white px-4 py-2 flex items-center gap-2 hover:bg-green-700"
          @click="saveEdits">
    <i class="fas fa-save"></i>
    <span>完成编辑</span>
  </button>
  <button type="button"
          class="!rounded-button bg-red-600 text-white px-4 py-2 flex items-center gap-2 hover:bg-red-700"
          @click="deleteSelectedRows">
    <i class="fas fa-trash-alt"></i>
    <span>批量删除</span>
  </button>
  <button type="button"
          class="!rounded-button bg-blue-600 text-white px-4 py-2 flex items-center gap-2 hover:bg-blue-700"
          @click="addNewRow">
    <i class="fas fa-plus"></i>
    <span>新增</span>
  </button>
        </div>
      </div>

<!-- 用户管理表格 -->
<div v-if="!isDishesTableVisible" id="dishes-table" class="relative overflow-x-auto shadow-md sm:rounded-lg">
  <table class="w-full text-sm text-left text-gray-500">
    <thead class="text-xs text-gray-700 uppercase bg-gray-50">
      <tr>
        <th scope="col" class="p-4">
          <div class="flex items-center"><input type="checkbox"
                                                class="w-4 h-4 text-custom bg-gray-100 border-gray-300 !rounded"
                                                v-model="selectAllUsers" /></div>
        </th>
        <th scope="col" class="px-6 py-3">用户ID</th>
        <th scope="col" class="px-6 py-3">用户名</th>
        <th scope="col" class="px-6 py-3">密码</th>
        <th scope="col" class="px-6 py-3">电话号码</th>
        <th scope="col" class="px-6 py-3">邮箱</th>
        <th scope="col" class="px-6 py-3">地址</th>
        <th scope="col" class="px-6 py-3">创建日期</th>
        <th v-if="isEditMode" scope="col" class="px-6 py-3">操作</th>
      </tr>
    </thead>
    <tbody>
      <tr class="bg-white border-b hover:bg-gray-50"
           v-for= "user in filteredUsers"
          :key="user.userid">
          <td class="w-4 p-4">
            <div class="flex items-center"><input type="checkbox"
                                                  class="w-4 h-4 text-custom bg-gray-100 border-gray-300 !rounded"
                                                  v-model="selectedUsers"
                                                  :value="user.userid" /></div>
          </td>
          <td class="px-6 py-4">{{ user.userid }}</td>
          <td class="px-6 py-4" :contenteditable="isEditMode" @blur="updateUser(user, 'username', $event.target.innerText)">{{ user.username }}</td>
          <td class="px-6 py-4" :contenteditable="isEditMode" @blur="updateUser(user, 'password', $event.target.innerText)">{{ user.password }}</td>
          <td class="px-6 py-4" :contenteditable="isEditMode" @blur="updateUser(user, 'phone', $event.target.innerText)">{{ user.phone }}</td>
          <td class="px-6 py-4" :contenteditable="isEditMode" @blur="updateUser(user, 'email', $event.target.innerText)">{{ user.email }}</td>
          <td class="px-6 py-4" :contenteditable="isEditMode" @blur="updateUser(user, 'address', $event.target.innerText)">{{ user.address }}</td>
          <td class="px-6 py-4">{{ user.createdate }}</td>
          <td v-if="isEditMode" class="px-6 py-4 flex gap-2">
            <button type="button"
                    class="text-red-600 hover:underline font-medium"
                    @click="deleteUser(index)">
              <i class="fas fa-trash-alt"></i>
            </button>
          </td>
      </tr>
    </tbody>
  </table>
</div>

<!-- 菜品管理表格 -->
<div v-if="isDishesTableVisible" id="dishes-table"
  class="relative overflow-x-auto shadow-md sm:rounded-lg">
  <table class="w-full text-sm text-left text-gray-500">
    <thead class="text-xs text-gray-700 uppercase bg-gray-50">
      <tr>
        <th scope="col" class="p-4">
          <div class="flex items-center"><input type="checkbox"
                                                class="w-4 h-4 text-custom bg-gray-100 border-gray-300 !rounded"
                                                v-model="selectAllDishes" /></div>
        </th>
        <th scope="col" class="px-6 py-3">菜品ID</th>
        <th scope="col" class="px-6 py-3">菜品名称</th>
        <th scope="col" class="px-6 py-3">价格</th>
        <th v-if="isEditMode" scope="col" class="px-6 py-3">操作</th>
      </tr>
    </thead>
    <tbody>
      <tr class="bg-white border-b hover:bg-gray-50"
          v-for="(dish, index) in dishes"
          :key="dish.id"
          :class="{'bg-gray-200': isEditMode && editedDishIndex === index}">
          <td class="w-4 p-4">
            <div class="flex items-center"><input type="checkbox"
                                                  class="w-4 h-4 text-custom bg-gray-100 border-gray-300 !rounded"
                                                  v-model="selectedDishes"
                                                  :value="dish.id" /></div>
          </td>
          <td class="px-6 py-4" :contenteditable="isEditMode" @blur="updateDish(dish, 'dishid', $event.target.innerText)">{{ dish.dishid }}</td>
          <td class="px-6 py-4" :contenteditable="isEditMode" @blur="updateDish(dish, 'name', $event.target.innerText)">{{ dish.name }}</td>
          <td class="px-6 py-4" :contenteditable="isEditMode" @blur="updateDish(dish, 'price', $event.target.innerText)">{{ dish.price }}</td>
          <td v-if="isEditMode" class="px-6 py-4 flex gap-2">
            <button type="button"
                    class="text-red-600 hover:underline font-medium"
                    @click="deleteDish(index)">
              <i class="fas fa-trash-alt"></i>
            </button>
          </td>
      </tr>
    </tbody>
  </table>
</div>
<!-- 分页控件 -->
<!-- 美化的分页控件 -->
<div class="mt-8 flex justify-center items-center space-x-4">
  <button @click="prevPage" :disabled="currentPage === 1"
          class="px-4 py-2 bg-gray-300 text-gray-700 rounded-lg hover:bg-gray-400 focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:bg-gray-200 disabled:text-gray-500">
    上一页
  </button>
  <span class="text-gray-600">第 {{ currentPage }} 页 / 共 {{ totalPages }} 页</span>
  <button @click="nextPage" :disabled="currentPage === totalPages"
          class="px-4 py-2 bg-gray-300 text-gray-700 rounded-lg hover:bg-gray-400 focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:bg-gray-200 disabled:text-gray-500">
    下一页
  </button>
</div>
    </div>
  </div>
</template>

<script>
import { ref, reactive, computed } from 'vue';

export default {
  setup() {
    const isSidebarOpen = ref(true);
    const isDishesTableVisible = ref(false);
    const isEditMode = ref(false);
    const originalDishes = ref([]); // 用于存储原始菜品数据的副本
    const originalUsers = ref([]); // 用于存储原始用户数据的副本
    const searchType = ref('');
    const searchQuery = ref('');
    const itemsPerPage = ref(10); // 每页显示的菜品数量
    const currentPage = ref(1); // 当前页码
    const totalItems = ref(0); // 从后端获取的总菜品数量
    const users = ref([]);
    const dishes = ref([]);
    const selectedUsers = ref([]);
    const selectedDishes = ref([]);
    const newUser = reactive({
      userid: '',
      username: '',
      password: '',
      phone: '',
      address: '',
      email: '',
      createdate: ''
    });
    const newDish = reactive({
      name: '',
      price: 0,
      description: '',
      img: ''
    });
    const editedUserIndex = ref(-1);
    const editedDishIndex = ref(-1);
    const columns = ['用户ID', '用户名', '电话', '地址', '邮箱', '创建日期'];
    const dishesColumns = ['菜品ID', '菜品名称', '价格'];

    const filteredUsers = computed(() => {
      if (!searchQuery.value) return users.value;
      if (searchType.value === '用户名') {
        return users.value.filter(
          user =>
            user.username &&
            user.username.toString().toLowerCase().includes(searchQuery.value.toLowerCase())
        );
      } else {
        return users.value.filter(
          user =>
            user[searchType.value] &&
            user[searchType.value].toString().toLowerCase().includes(searchQuery.value.toLowerCase())
        );
      }
    });

    const filteredDishes = computed(() => {
      if (!searchQuery.value) return dishes.value;
      return dishes.value.filter(
        dish =>
          dish[searchType.value] &&
          dish[searchType.value].toString().toLowerCase().includes(searchQuery.value.toLowerCase())
      );
    });

    const totalPages = computed(() => {
      return Math.ceil(totalItems.value / itemsPerPage.value);
    });

    const selectAllUsers = computed({
      get() {
        return users.value.length && users.value.every(user => selectedUsers.value.includes(user.userid));
      },
      set(value) {
        if (value) {
          selectedUsers.value = users.value.map(user => user.userid);
        } else {
          selectedUsers.value = [];
        }
      }
    });

    const selectAllDishes = computed({
      get() {
        return dishes.value.length && dishes.value.every(dish => selectedDishes.value.includes(dish.dishid));
      },
      set(value) {
        if (value) {
          selectedDishes.value = dishes.value.map(dish => dish.dishid);
        } else {
          selectedDishes.value = [];
        }
      }
    });

    const isUsersActive = computed(() => !isDishesTableVisible.value);
    const isDishesActive = computed(() => isDishesTableVisible.value);

    function toggleSidebar() {
      isSidebarOpen.value = !isSidebarOpen.value;
    }

    function showUsers() {
      isDishesTableVisible.value = false;
      searchType.value = '用户ID'; // 用户管理的默认搜索类型
      selectedUsers.value = [];
      editedUserIndex.value = -1;
      isEditMode.value = false;
    }

    function showDishes() {
      isDishesTableVisible.value = true;
      searchType.value = '菜品ID'; // 菜品管理的默认搜索类型
      selectedDishes.value = [];
      editedDishIndex.value = -1;
      isEditMode.value = false;
    }

    function toggleEditMode() {
      isEditMode.value = !isEditMode.value;
      if (isEditMode.value) {
        // 进入编辑模式时，备份原始数据
        originalDishes.value = JSON.parse(JSON.stringify(dishes.value));
        originalUsers.value = JSON.parse(JSON.stringify(users.value));
      }
    }
        function saveDishes() {
            // Logic for saving dishes
            console.log('Dishes saved!');
        }

        function saveUsers() {
            // Logic for saving users
            console.log('Users saved!');
        }

    function saveEdits() {
      if (isDishesTableVisible.value) {
        saveDishes();
      } else {
        saveUsers();
      }
      originalDishes.value = JSON.parse(JSON.stringify(dishes.value));
      originalUsers.value = JSON.parse(JSON.stringify(users.value));
      isEditMode.value = false; // 退出编辑模式
    }

    function cancelEdits() {
      dishes.value = originalDishes.value.filter(dish => dishes.value.includes(dish));
      isEditMode.value = false;
    }

    function addNewRow() {
      if (isDishesTableVisible.value) {
        dishes.value.push({
          name: '',
          price: 0,
          description: '',
          img: ''
        });
      } else {
        users.value.push({
          username: '',
          password: '',
          phone: '',
          address: '',
          email: '',
          createdate: ''
        });
      }
    }

    // 其他方法根据需要进行转换...

    return {
      isSidebarOpen,
      isDishesTableVisible,
      isEditMode,
      originalDishes,
      originalUsers,
      searchType,
      searchQuery,
      itemsPerPage,
      currentPage,
      totalItems,
      users,
      dishes,
      selectedUsers,
      selectedDishes,
      newUser,
      newDish,
      editedUserIndex,
      editedDishIndex,
      columns,
      dishesColumns,
      filteredUsers,
      filteredDishes,
      totalPages,
      selectAllUsers,
      selectAllDishes,
      isUsersActive,
      isDishesActive,
      toggleSidebar,
      showUsers,
      showDishes,
      toggleEditMode,
      saveEdits,
      cancelEdits,
      addNewRow
    };
  }
};
</script>


<style>
/* 添加一些样式来区分侧边栏按钮的状态 */
#logo-sidebar a {
  transition: background-color 0.3s, color 0.3s;
}

#logo-sidebar a.bg-custom.text-white {
  background-color: #9ca3af; /* 灰色背景 */
  color: #1f2937; /* 灰色文字 */
}


/* 其他样式 */
</style>
