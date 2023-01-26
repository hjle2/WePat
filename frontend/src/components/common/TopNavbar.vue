<template>
    <img alt="Logo" @click="toMain" src="@/assets/logo.png" />
</template>
  
  <script>
  import router from "@/router"
  export default {
    name : 'TopNavbar',
    computed: {
       
       },
    methods: {
        //로그인 화면에서 메인 접근 방어
        toMain() {
            const loginUrl = "http://localhost:8080/"
            const para = document.location.href;
            // console.log(para);
            if (para !== loginUrl) {
                router.push({ path: '/mainpage' })
                }
            },
            
        //로그인 상태 확인
        isLogin() {
          if (this.$store.getters.isLogin) {
            console.log("로그인중")
          }
          else {
            console.log("로그인되어 있지 않음")
            const loginUrl = "http://localhost:8080/"
            const para = document.location.href;
            if (para !== loginUrl) {
              alert("로그인이 필요합니다. 로그인 페이지로 이동합니다.")
              router.push({ path: '/' })
            }
          }
        },
    },
    created() {
      this.isLogin()
    }
  }
  </script>
  
  <style></style>