import { createApp } from "vue"
import App from "./App.vue"
import { Chrome, create } from '@ckpack/vue-color';
import router from "./router/index"
import store from "./store/storage"
import globals from "./commonGlobal/global"


createApp(App).use(create({
    components: [Chrome],
  })).use(store).use(router).use(globals).mount("#app")