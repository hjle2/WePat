<template>
  <p>55 메인페이지 아인교</p>
  <p>{{ NickName }}의 캘린더 아잉교</p>
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
import axios from "axios"

export default {
  name: "MainView",
  data() {
    return {
      NickName: null,
    }
  },
  components: {
    MainCalendar,
    TodoList,
    PetIcon,
    BottomNavbar1
  },
  computed: {

  },
  methods: {
    getNickname() {
      axios({
          method: 'get',
          url: `http://70.12.247.124:8080/member/${localStorage.getItem("memberId")}`,
          headers: {Authorization: this.$store.state.accessToken},
        })
        .then((res) => {
          this.NickName = res.data.nickName
        })
        .catch((err) => {
              console.log(err)
              console.log(err.response.status)
              if (err.response.status === 401) {
                if (localStorage.getItem("token")) {
                  this.$store.dispatch('refresh', localStorage.getItem("token"))
                  this.dispatch('getNickname')
                }
              }
            })
    }
  },
  created() {
    this.getNickname()
  }
  
}
</script>
<style>
</style>