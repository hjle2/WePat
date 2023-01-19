import { createApp } from "vue"
import App from "./App.vue"
import router from "./router/index"
import store from "./store/storage"
import globals from "./commonGlobal/global"

createApp(App).use(store).use(router).use(globals).mount("#app")