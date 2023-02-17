<template>
<div class="holder-vanilla-op">
  <router-link  to="/user/detail">
      <img src="@/assets/back.png" class="logo-back" alt="">
    </router-link>
    <br><br>
  <!-- <div class="holder-text-input"> -->
  
    <p class="font-medium text-center"> "{{ check }}"</p>
    <p class="text-input text-center">위의 문구를 그대로 입력한 후,</p>
    <p class="text-input text-center">생성하기 버튼을 누르세요.</p>

  
  <br>
  <!-- <p class="text-input">  </p> -->
  <input class="font-mini input input-change-new" type="text" id="check2" v-model="check2" placeholder="새 캘린더 코드를 생성하겠습니다.">
  <!-- </div> -->
  <br><br>
  <button v-if="check === check2 " class="btn-colored btn-medium btn-Todo" @click="createCalendar()">생성하기</button>
  <button v-if="check !== check2" class="btn-disabled btn-medium btn-Todo">생성하기</button>
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
      check: "새 캘린더 코드를 생성하겠습니다.",
      check2: null,
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
     createCalendar() {
      Swal.fire({
              title: "새 캘린더 생성",
              text: "정말로 생성하시겠습니까?",
              icon: "warning",

              showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
              confirmButtonColor: "#faa960", // confrim 버튼 색깔 지정
              cancelButtonColor: "#d33", // cancel 버튼 색깔 지정
              confirmButtonText: "예", // confirm 버튼 텍스트 지정
              cancelButtonText: "아니오", // cancel 버튼 텍스트 지정

              reverseButtons: true, // 버튼 순서 거꾸로
            }).then((result) => {
              if (result.isConfirmed) {
              this.$store.dispatch('createCalendar')
            }
     })
  },
  },
  created() {
    this.getUserData()
  }
}
</script>

<style>
.input-change-new {
  width: 70vw !important;
  right: 15vw;
  position: absolute;
}

.text-change {
  text-align: left;
  margin-left: 15vw !important;
}
</style>