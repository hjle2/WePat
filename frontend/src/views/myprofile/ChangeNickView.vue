<template>
<div class="holder-vanilla-op">
  <router-link  to="/user/detail">
      <img src="@/assets/back.png" class="logo-back" alt="">
    </router-link>
    <br><br><br>
  <div class="">
  <div> 
    <div class="">
      <p class="font-medium text-change" @keyup.enter="popOpen" for="newNick"> 새 닉네임 </p> 
      </div>
      <input class="font-medium input input-change" type="text" id="newNick" v-model="newNick">
    </div><br><br>
    <p class="font-small font-grey"> 변경할 새 닉네임을 입력하세요.</p>
    <div class="holder-category">
        <p class="font-lefts"></p>
      </div><br>
      <button v-if="newNick" class="btn-colored btn-medium btn-Todo" @click="changeUser()">변경하기</button>
      <button v-if="!newNick" class="btn-disabled btn-medium btn-Todo">변경하기</button>
  </div>
</div>
</template>

<script>
// import axios from 'axios'
import Swal from "sweetalert2"
export default {
  data: function () {
    return {
    nickName : "",
    newNick:"",
    }
  },
  methods:{
    changeUser() {
      Swal.fire({
            title: "닉네임 변경",
            text: "정말로 변경하시겠습니까?",
            icon: "warning",

            showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
            confirmButtonColor: "#faa960", // confrim 버튼 색깔 지정
            cancelButtonColor: "#d33", // cancel 버튼 색깔 지정
            confirmButtonText: "예", // confirm 버튼 텍스트 지정
            cancelButtonText: "아니오", // cancel 버튼 텍스트 지정

            reverseButtons: true, // 버튼 순서 거꾸로
          }).then((result) => {
            if (result.isConfirmed) {
              this.$store.dispatch('changeNickname', this.newNick)
            }
          })
    },
   
  },
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