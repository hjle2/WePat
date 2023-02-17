import { createApp } from "vue"
import App from "./App.vue"
import { Chrome, create } from '@ckpack/vue-color';
import router from "./router/index"
import store from "./store/storage"
import globals from "./commonGlobal/global"
import mitt from 'mitt'
import VueClipboard from 'vue-clipboard2'
import axios from "axios";

// import Meta from 'vue-meta'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css'
import BootstrapVue3 from 'bootstrap-vue-3'
import VueSweetalert2 from 'vue-sweetalert2';


axios.interceptors.request.use(function (config) {
  if (config.method==="get") {
    store.commit('LOADING_STATUS', true)
    setTimeout(() => 
      {if (store.state.loadingStatus === true) {
        router.go(0)    
      }}, 10000);
  }
  return config
});
axios.interceptors.response.use(function (config) {
  // store.commit('LOADING_STATUS', false)
    setTimeout(() => store.commit('LOADING_STATUS', false), 1000);
  return config
});


VueClipboard.config.autoSetContainer = true;


export const emitter = mitt();
const app = createApp(App);
app.config.globalProperties.emitter = emitter;
// app.config.globalProperties.axios = axios;
app.use(create({
    components: [Chrome],
  })).use(store).use(router).use(globals).use(BootstrapVue3).use(VueClipboard).use(VueSweetalert2).mount("#app")
