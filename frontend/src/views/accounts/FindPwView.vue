<template>
  <div class="holder-vanilla-default">
    <router-link  to="/">
        <img src="@/assets/back.png" class="logo-back" alt="">
      </router-link>
      <br>
      <div class="logo-wepat">
        <img src="@/assets/logo/dog_logo_with_wepat_orange.png" alt="" style="width: 30vw">
      </div>

    <br />
    <div class="holder-text-input">
      <p class="font-small text-find" @keyup.enter="find" for="email">이메일</p>
    </div>
    <input
     class="input-login"
      type="text"
      memberId="email"
      v-model="email"
    />
    <p class="font-small font-grey">
      이메일로 비밀번호 변경 메일이 전송됩니다.
    </p>
    <div class="holder-text-input">
      <p class="font-small text-find" @keyup.enter="find" for="memberId">아이디</p>
    </div>
    <input
     class="input-login"
      type="text"
      memberId="memberId"
      v-model="memberId"
    />
    <br><br>
    <!-- <p v-if="send" class="font-small">사용자님의 이메일로 임시 비밀번호를 발송하였습니다.</p>
    <br v-if="send"><br v-if="send"> -->
    <div class="">
      <button class="btn-colored btn-medium input-btn" @click="findPwd">
        비밀번호 찾기
      </button>
    </div>
    <br />
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: "",
      memberId: "",
      send: false,
    }
  },
  methods: {
    findPwd() {
      const payload = {
        email: this.email,
        memberId: this.memberId,
      }
      console.log(payload)
      this.$store.dispatch("findPwd", payload)
      this.send = true
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
