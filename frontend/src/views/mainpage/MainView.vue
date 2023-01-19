<template>
  <p>55 메인페이지 아인교</p>
  <p>{{ UserNickName }}의 캘린더 아잉교</p>
  <div @click="LogOut">로그아웃</div>
  <br />
  <MainCalendar/>
  <TodoList/>
  <router-link to="/todo/add">
    <img alt="add_todo" src="@/assets/add_todo.png">
  </router-link>
  <br />
  <router-link to="/alarm">
    <img alt="icon_bell" src="@/assets/icon_bell.png">
  </router-link>
  <br />

  <router-link to="/todo/detail">
    <button>일정 상세페이지</button>
  </router-link>
  <br />
  <router-link to="/todo/update">
    <button>일정 수정페이지</button>
  </router-link>
  <br />
    <PetIcon/>
  <br />
    <BottomNavbar1/>
</template>

<script>
import MainCalendar from "@/components/mainpage/MainCalendar.vue"
import TodoList from "@/components/mainpage/TodoList.vue"
import PetIcon from "@/components/mainpage/PetIcon.vue";
import BottomNavbar1 from "@/components/common/BottomNavbar1.vue";

export default {
  name: "MainView",
  data() {
    return {
      UserNickName: null,
    }
  },
  components: {
    MainCalendar,
    TodoList,
    PetIcon,
    BottomNavbar1
  },
  computed: {
    isLogin() {
      return this.$store.getters.isLogin
    }
  },
  methods: {
    LoginCheck() {
      console.log(this.isLogin)
      if (this.isLogin) {
        console.log('로그인 되어 있음.')
        this.UserNickName = localStorage.getItem("NICKNAME")
      }
      else {
        alert('로그인이 되어있지 않습니다. 로그인 페이지로 이동합니다.')
        this.$router.replace({ path: '/' })
      }
    },
    LogOut() {
      this.$store.dispatch('logOut')
    },

  },
  created() {
    this.LoginCheck()
  }
  
}
</script>
<style>
</style>