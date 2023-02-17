<template>
<div class="holder-vanilla-op">
  <router-link  to="/user/detail">
      <img src="@/assets/back.png" class="logo-back" alt="">
    </router-link>
    <br><br>
  <div class="">
  <!-- <p class="text-input">현재 캘린더 코드</p>
  <p class="font-medium">{{ this.calendarId }}</p> -->

  
  <br>
  <p class="font-medium">공유받은 캘린더 코드 </p>
  <input class="font-mini input input-change" type="text" id="newCalendarId" v-model="newCalendarId" placeholder="코드를 입력하세요">
  </div>
  <br><br>
  <button v-if="newCalendarId" class="btn-colored btn-medium btn-Todo" @click="changeCalendar()">변경하기</button>
  <button v-if="!newCalendarId" class="btn-disabled btn-medium btn-Todo">변경하기</button>
  <br><br>
  <div class="holder-category">
  </div>
  <br>
  <p class="font-small font-grey"> {{ nickName }}님의 기존 캘린더는 삭제됩니다. </p>

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
      newCalendarId: null,
    }
  },
  methods: {
    getUserData() {
      const dataSet = this.$store.dispatch('getUserData')
      // console.log("받은 것" + dataSet)
      dataSet.then((getData) => {
        this.nickName = getData.nickName
        this.calendarId = getData.calendarId
        return getData.calendarId
      })
    },
    changeCalendar() {
      Swal.fire({
            title: "캘린더 이동",
            text: "정말로 이동하시겠습니까?",
            icon: "warning",

            showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
            confirmButtonColor: "#faa960", // confrim 버튼 색깔 지정
            cancelButtonColor: "#d33", // cancel 버튼 색깔 지정
            confirmButtonText: "예", // confirm 버튼 텍스트 지정
            cancelButtonText: "아니오", // cancel 버튼 텍스트 지정

            reverseButtons: true, // 버튼 순서 거꾸로
          }).then((result) => {
            if (result.isConfirmed) {
              this.$store.dispatch('changeCalendar', this.newCalendarId)
            }
          })
    },
  },
  created() {
    this.getUserData()
  }
}
</script>

<style lang="scss">
.input-change {
  width: 50vw !important;
  // float: right;
  right: 25vw;
  position: absolute;
}

.text-change {
  text-align: left;
  margin-left: 15vw !important;
}
</style>