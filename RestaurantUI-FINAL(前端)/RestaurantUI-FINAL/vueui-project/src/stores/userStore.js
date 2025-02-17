// // src/stores/userStore.js
// import { defineStore } from 'pinia';

// export const useUserStore = defineStore('user', {
//     state: () => ({
//         isLoggedIn: false,
//         userAvatar: '',  // 保存头像路径
//     }),
//     actions: {
//         login(avatarUrl) {
//             this.isLoggedIn = true;
//             this.userAvatar = avatarUrl;
//         },
//         logout() {
//             this.isLoggedIn = false;
//             this.userAvatar = '';
//         },
//     },
// });
