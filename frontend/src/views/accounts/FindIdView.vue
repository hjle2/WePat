<template>
  <div class="holder-vanilla-default">
      <router-link  to="/">
        <img src="@/assets/back.png" class="logo-back" alt="">
      </router-link>
      <br>   
      <div class="logo-wepat">
        <img src="@/assets/logo/dog_logo_with_wepat_orange.png" alt="" style="width: 30vw">
      </div>
      
    <div>
      <!-- <p class="font-medium">아이디 찾기</p> -->
      <br>
      <div class="holder-text-input">
      <p class="text-find font-small" @keyup.enter="find" for="email"> 이메일 </p> 
      </div>
      <p></p>
      <input class="input-login" type="text" id="email" v-model="email">
      <p class="font-small font-grey"> 회원가입되어 있는 이메일을 입력하세요.</p><br>
      <br v-if="memberId === null">  
      <p class="font-small" v-if="memberId" > 사용자님의 아이디는 {{ memberId }} 입니다.</p>
      <p class="font-small font-grey" v-if="memberId" > 터치하여 아이디를 복사하세요. </p>
      <br v-if="memberId === null">  
      <br v-if="memberId === null">  
      <br>
      <div class="">
      <button class="btn-colored btn-medium input-btn" @click="findId">아이디 찾기</button>
      </div>
      <br>

    </div>
  
  </div>
</template>
  
<script>
export default {
data() {
    return {
      email: "",
      memberId: "",
    }
  },
  methods: {
    
    findId() {
      console.log(this.email)
      console.log("찾기 시작")
      const dataSet = this.$store.dispatch("findId", this.email)
      dataSet.then((getData) => {
        if (getData) {
        console.log(getData)
        this.memberId = getData
        }
        return this.memberId
      })
    },
  },
}
</script>

<style lang="scss">
.input-login {
  width: 60vw !important;
  height: 7vh;
  border-radius: $radius-size-2;
  border: 1vh solid $color-main-75;
  margin: 0;
  padding-left: 5vw;
}

.input-btn {
  width: 60vw !important;
  height: 7vh;
  border-radius: $radius-size-2;
  border: 1vh solid $color-main-75;
  margin: 0;
  // padding-left: 5vw;
}
.text-find {
  // text-align: left !important;
  margin-left: -55vw !important;
  margin-bottom: 0;
  margin-top: 5vh;
  // left: 0vw !important;
}
</style>
