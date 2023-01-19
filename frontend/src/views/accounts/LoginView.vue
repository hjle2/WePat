<template>
  <div>
    <form @submit.prevent="logIn">
      <label class="inputText" @keyup.enter="logIn" for="memberId"> ID :  </label> <input class="input inputLogin" type="text" id="memberId" v-model="memberId"><br>
      <label class="inputText" @keyup.enter="logIn" for="pwd"> PW :  </label> <input class="input inputLogin" type="pwd" id="pwd" v-model="pwd"><br>
      <button type="submit" >로그인</button>
    </form>
  <br /> 
    <router-link to="/findid">
      <button>아이디찾기</button>
    </router-link>
    <br />
    <router-link to="/findpw">
      <button>비밀번호찾기</button>
    </router-link>
    <br />
    <router-link to="/signup">
      <button>회원가입</button>
    </router-link>
  </div>
</template>

<script>
export default {
  name: 'LoginView',
  data() {
    return {
      memberId: null,
      pwd: null,
    }
  },
  computed: {
    isLogin() {
      return this.$store.getters.isLogin
    }
  },
  methods: {
    logIn() {
      const memberId = this.memberId
      const pwd = this.pwd
      if (memberId.length === 0) {
        alert('아이디를 입력해주세요')
        return
      } else if (pwd.length === 0) {
        alert('비밀번호를 입력해주세요')
        return
      }
      else {
        const payload = {
          memberId: memberId,
          pwd: pwd,
        }
        // console.log(payload)
        this.$store.dispatch('login', payload)
      }
    },
    Check() {
      if (this.isLogin) {
        console.log('로그인 되어 있음. 메인페이지로 보냄')
        this.$router.replace({ path: '/mainpage' })
      }
    },
    SignUp() {
      this.$router.push({name: 'SignUpView'})
    }
  },
  created() {
    this.Check()
  }
}
</script>

<style>
</style>