<template>
  <div class="holder-vanilla-op">
  <router-link  to="/user">
      <img src="@/assets/back.png" class="logo-back" alt="">
    </router-link>
    <br><br><br>
  <p class="font-small text-center">터치하여 캘린더 코드를 복사하세요.</p><br>
  <p class="font-medium text-center" @click="copy">{{ this.calendarId }}</p>
  
  <br>
  <div class="holder-category">
  </div>
  <br>
  <p class="font-small font-grey"> 회원정보 → 기존 캘린더 가입 → 코드 입력</p>
  <p class="font-small font-grey"> 혹은 회원가입 시 캘린더 코드 입력으로</p>
  <p class="font-small font-grey"> 해당 캘린더에 초대할 수 있습니다.</p>

  </div>
  
</template>

<script>
import Swal from "sweetalert2"
export default {
  name: "InviteView",
  data() {
    return {
      nickName: null,
      calendarId: null,
    }
  },
  methods: {
    copy() {
      this.$copyText(this.calendarId).then(()=> {
        Swal.fire({
            icon: "success",
            title: "복사 완료",
            text: "붙여넣기로 코드를 공유하세요!",
            timer: 1500,
            timerProgressBar: true,
            showConfirmButton: false,
          })
      })
    },
    getUserData() {
      const dataSet = this.$store.dispatch('getUserData')
      // console.log("받은 것" + dataSet)
      dataSet.then((getData) => {
        this.nickName = getData.nickName
        this.calendarId = getData.calendarId
        console.log(this.nickName)
        console.log(this.calendarId+"@@@@@@@@@")
        return getData.calendarId
      })
    },
  },
  created() {
    this.getUserData()
  }
}
</script>

<style>

</style>