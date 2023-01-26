<template>
<div>
  <div> <span>Email</span> <input type="text" v-model="email">
  </div>
  <div> <span>Id</span> <input type="text" v-model="memberId">
  </div>
  <div> <span>nickName</span> <input type="text" v-model="nickName">
  </div>
  <div> <span>캘린더 초대코드</span> <input type="text" v-model="calenderId">
  </div>
  <div> <span>Password</span> <input type="password" v-model="pwd">
  </div>
  <div> <span>Password 확인</span> <input type="password" v-model="pwd2">
  </div>
  <router-link to="/agree">
      <button>약관동의 보기</button>
  </router-link>
      <button @click="signUp()">가입하기</button>
</div>
</template>

<script>
export default {
  data: function () {
    return {
    email:"",
    memberId:"",
    nickName:"",
    calenderId:"",
    pwd:"",
    pwd2:"",
    }
  },
  methods:{
    signUp() {
      if (this.pwd.length < 8 || this.pwd2.length < 8) {
        alert('비밀번호 8자리 이상 적어주세요')
      }
      else if (this.pwd === this.pwd2) {
        const email = this.email
        const memberId = this.memberId
        const nickName = this.nickName
        const calendarId = this. calendarId
        const pwd = this.pwd

        const payload = {
          email, memberId, nickName, calendarId, pwd
        }
        this.$store.dispatch('signUp', payload)
      }
      else {
        alert('비밀번호 확인해주세요')
      }
    
    },
  }
}
</script>

<style>

</style>