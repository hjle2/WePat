<template>
<div class="holder-vanilla-op">
  <router-link  to="/user/detail">
      <img src="@/assets/back.png" class="logo-back" alt="">
    </router-link>
    <br>
  <div class="">
  <div>
    <p class="font-medium text-change" for="pwd"> 기존 비밀번호 </p> 
    <input class="font-medium input input-change" type="password" @keyup.enter="popClose" v-model="pwd" id="pwd">
  </div>
  <br><br>
  <div>
    <p class="font-medium text-change" for="newPwd"> 새 비밀번호 </p> 
    <input class="font-medium input input-change" type="password" @keyup.enter="popClose" v-model="newPwd" id="newPwd">
  </div>
  <p class="font-small font-grey"> 비밀번호는 8자 이상,</p>
  <p class="font-small font-grey"> 특수문자는 !, @, #, $, %, ^, &, * 만 사용 가능합니다.</p>
  <br>
  <div>
    <p class="font-medium text-change" for="newPwd2"> 비밀번호 확인</p> 
    <input class="font-medium input input-change" type="password" @keyup.enter="popClose" v-model="newPwd2" id="newPwd2">
  </div>
  <br><br>
  <div class="holder-category">
        <p class="font-lefts"></p>
      </div><br>
    <button v-if="newPwd && (newPwd === newPwd2)" class="btn-colored btn-medium btn-Todo" @click="changeUser()">변경하기</button>
    <button v-if="!newPwd || newPwd !== newPwd2" class="btn-disabled btn-medium btn-Todo">변경하기</button>
  </div>
</div>
</template>

<script>
import Swal from "sweetalert2"
// import axios from 'axios'
export default {
  data: function () {
    return {
    pwd: "",
    pwdCheck: "",
    newPwd:"",
    newPwd2:"",
    reg_pwd: /[`~()_|+\-=?;:'",.<>/{}[] \]/gim, //pwd에 사용가능한 문자 제거
    }
  },
  methods:{
    changeUser() {
      if (this.newPwd.length < 8 || this.newPwd2.length < 8) {
        Swal.fire({
               title: "변경 오류",
              text:  "비밀번호 8자리 이상 적어주세요",
              icon: "warning",

              // showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
              confirmButtonColor: "#faa960", // confrim 버튼 색깔 지정
              // cancelButtonColor: "#d33", // cancel 버튼 색깔 지정
              confirmButtonText: "확인", // confirm 버튼 텍스트 지정
              // cancelButtonText: "아니오", // cancel 버튼 텍스트 지정

              // reverseButtons: true, // 버튼 순서 거꾸로
            })
      } else {
        if (this.reg_pwd.test(this.newPwd)) {
          Swal.fire({
               title: "변경 오류",
              text:  "비밀번호 특수문자는 !, @, #, $, %, ^, &, * 만 사용 가능합니다.",
              icon: "warning",

              // showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
              confirmButtonColor: "#faa960", // confrim 버튼 색깔 지정
              // cancelButtonColor: "#d33", // cancel 버튼 색깔 지정
              confirmButtonText: "확인", // confirm 버튼 텍스트 지정
              // cancelButtonText: "아니오", // cancel 버튼 텍스트 지정

              // reverseButtons: true, // 버튼 순서 거꾸로
            })
        }
        else {    
          if (this.newPwd === this.newPwd2) {
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
                const newPwd = this.newPwd
                const pwd = this.pwd
                const payload = {
                  pwd,
                  newPwd
                 }
                this.$store.dispatch('changePwd', payload)
                }
            })            
           } else {
             Swal.fire({
               title: "변경 오류",
              text: "새 비밀번호가 서로 다릅니다.",
              icon: "warning",

              // showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
              confirmButtonColor: "#faa960", // confrim 버튼 색깔 지정
              // cancelButtonColor: "#d33", // cancel 버튼 색깔 지정
              confirmButtonText: "확인", // confirm 버튼 텍스트 지정
              // cancelButtonText: "아니오", // cancel 버튼 텍스트 지정

              // reverseButtons: true, // 버튼 순서 거꾸로
            })
            }
          }
        }
    },
    popOpen() {
        document.getElementsByClassName("modal-wrap")[0].style.display ='block';
        document.getElementsByClassName("modal-bg")[0].style.display ='block';
    },
    popClose() {
        document.getElementsByClassName("modal-wrap")[0].style.display ='none';
        document.getElementsByClassName("modal-bg")[0].style.display ='none';
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