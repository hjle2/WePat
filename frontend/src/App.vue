<template>
  <transition :name="transitionName">
    <router-view />
  </transition>
  <LoadingView :loading="this.$store.state.loadingStatus===true"/>
</template>

<script>
import Swal from "sweetalert2"
import LoadingView from "@/views/accounts/LoadingView.vue"
export default {
  name: "App",
  data() {
    return {
      transitionName: "fade",
    }
  },
  components: {
    LoadingView,
},
  // watch: {
  //   $route(to, from) {
  //     if(this.$store.getters.isLogin){
  //       console.log(document.location.href)
  //       if (document.location.href === "http://localhost:8080/mainpage/aaa") {
  //         this.transitionName = "next";
  //       } else {
  //         this.transitionName = "next";
  //       }
  //     } else {
  //       this.transitionName = "next";
  //     }
  //     console.log(to)
  //     console.log(from)
  //     console.log(this.transitionName);
  //   }
  // },
  methods: {
    isLogin() {
      if (this.$store.getters.isLogin) {
        // console.log("로그인중")
          }
          else {
            // console.log("로그인되어 있지 않음")
            // const API_URL = "http://localhost:8080/"
            const API_URL = "https://i8a607.p.ssafy.io/api"
            // const API_URL = "http://i8a607.p.ssafy.io:8080/api"
            // const API_URL = 'http://192.168.100.12:8080/api'
            // const API_URL = "http://i8a607.p.ssafy.io:8080"
            // const API_URL = "http://70.12.247.105:8080/api"
            // const API_URL = "http://70.12.247.102:8080/api"
            // const API_URL = "wepat.netlify.app"
            // const API_URL = "https://wepat.site"
            const loginUrl = `${API_URL}`
            const signUpUrl = `${API_URL}signup`
            const findIdUrl = `${API_URL}findid`
            const findPwdUrl = `${API_URL}findpw`
            const para = document.location.href

            if (para !== API_URL && para !== loginUrl && para !== findPwdUrl && para !== findIdUrl && para !== signUpUrl) {
              Swal.fire({
                icon: "warning",
                title: '로그인이 필요합니다!',
                text: '관련 페이지로 이동합니다.',
                timer: 1600,
                timerProgressBar: true,
                showConfirmButton: false,
              }).then(
                this.$router.push({ path: '/' }),
                localStorage.removeItem("vuex"),
              )
            }
          }
        },
  },
  created() {
    this.isLogin()
  },
}
</script>

<style lang="scss">
</style>
