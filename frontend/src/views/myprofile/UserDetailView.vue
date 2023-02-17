<template>
<div id="UserDetailView">
  <div class="holder-vanilla-op">
    <router-link to="/user">
      <img src="@/assets/back.png" class="logo-back" alt="" />
    </router-link>
    <div class="">
      <div class="holder-category">
        <p class=""></p>
      </div>
      <div class="font-vanila font-small at-bottom">
        <router-link
          class="holder-asset font-vanila "
          to="/user/nick"
        >
          <p class=" ">닉네임 변경</p>
          <p class="">></p>
        </router-link>
        <router-link
          class="holder-asset font-vanila "
          to="/user/pw"
        >
          <p class=" ">비밀번호 변경</p>
          <p class="">></p>
        </router-link>
        <router-link
          class="holder-asset font-vanila "
          to="/invite"
        >
          <p class=" ">기존 캘린더 가입</p>
          <p class="">></p>
        </router-link>
        <router-link
          class="holder-asset font-vanila "
          to="/user/new"
        >
          <p class=" ">새 캘린더 생성</p>
          <p class="">></p>
        </router-link>
        <br /><br />
        <div class="holder-category">
          <p class=""></p>
        </div>
        <div class="holder-asset font-vanila cursor-pointer" @click="logOut">
          <p class=" " >로그아웃</p>
          <p class="">></p>
        </div>
        <div class="holder-asset font-vanila cursor-pointer" @click="deleteUser()">
          <p class=" " >
            회원탈퇴
          </p>
          <p class="">></p>
        </div>
      </div>
      <br />
    </div>
    <br />
  </div>
  <BottomNavbar5 />
  </div>
</template>

<script>
import BottomNavbar5 from "@/components/common/BottomNavbar5.vue"
import Swal from "sweetalert2"
export default {
  name: "UserDetailView",
  data() {
    return {
      nickName: null,
      email: null,
      calendarId: null,
      warnMemberList: null,
      blockMemberList: null,
    }
  },
  components: {
    BottomNavbar5,
  },
  computed: {},
  methods: {
    logOut() {
      Swal.fire({
              title: "로그아웃",
              text: "정말로 로그아웃하시겠습니까?",
              icon: "warning",

              showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
              confirmButtonColor: "#faa960", // confrim 버튼 색깔 지정
              cancelButtonColor: "#d33", // cancel 버튼 색깔 지정
              confirmButtonText: "예", // confirm 버튼 텍스트 지정
              cancelButtonText: "아니오", // cancel 버튼 텍스트 지정

              reverseButtons: true, // 버튼 순서 거꾸로
            }).then((result) => {
              if (result.isConfirmed) {
              this.$store.dispatch("logOut")
            }})
    },
    deleteUser() {
      Swal.fire({
              title: "계정 삭제",
              text: "정말로 삭제하시겠습니까?",
              icon: "warning",

              showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
              confirmButtonColor: "#faa960", // confrim 버튼 색깔 지정
              cancelButtonColor: "#d33", // cancel 버튼 색깔 지정
              confirmButtonText: "예", // confirm 버튼 텍스트 지정
              cancelButtonText: "아니오", // cancel 버튼 텍스트 지정

              reverseButtons: true, // 버튼 순서 거꾸로
            }).then((result) => {
              if (result.isConfirmed) { 
              this.$store.dispatch("deleteUser")
            }})
    },
  },
  created() {},
}
</script>

<style lang="scss">
.cursor-pointer:hover {
  cursor: pointer;
  color: blue
}
</style>
