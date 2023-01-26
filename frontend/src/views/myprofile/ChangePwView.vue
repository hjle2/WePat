<template>
  <div>
  <p>비밀번호 변경페이지</p>
  <!-- <div> <span>기존 비밀번호</span> <input type="text" v-model="pwdCheck">
  </div> -->
  <div> <span>새 비밀번호</span> <input type="password" v-model="newPwd">
  </div>
  <div> <span>새 비밀번호 확인</span> <input type="password" v-model="newPwd2">
  </div>
      <button @click="ChangeUser()">변경하기</button>
  </div>
</template>

<script>
// import axios from 'axios'
export default {
  data: function () {
    return {
    // pwd: "",
    pwdCheck: "",
    newPwd:"",
    newPwd2:"",
    }
  },
  methods:{
    ChangeUser() {
      // if (this.pwd !==this.pwdCheck) {
      //   alert('기존 비밀번호가 다릅니다!')
      // }
      // else 
      if (this.newPwd === this.newPwd2) {
        const newPwd = this.newPwd

        const payload = {
          newPwd
        }
        this.$store.dispatch('changePwd', payload)
      }
      else {
        alert('새 비밀번호가 서로 다릅니다!')
      }
    },
    // 비밀번호 암호화되어 오므로 지금은 불필요
    // GetPwd() {
    //   const API_URL = 'http://70.12.247.124:8080'
    //   axios({
    //     method: 'get',
    //     url: `${API_URL}/member/${localStorage.getItem("memberId")}`,
    //   })
    //     .then((res) => {
    //       console.log(res.data)
    //       this.pwd = res.data.pwd
    //     })
    // },
  },
  created() {
    // this.GetPwd()
  }
}
</script>

<style>

</style>