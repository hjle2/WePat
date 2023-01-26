<template>
  <p>프로필</p>
  <div @click="Invite">캘린더 초대 코드
    <p>{{ calendarId }}</p>
  </div>
  <div @click="LogOut">로그아웃</div>
  <div @click="DeleteUser">회원탈퇴</div>
  <div @click="ChangeNickname">닉네임 변경</div>

  <router-link to="/pet/add">
  <button> 동물 추가 페이지 </button>
  </router-link>
  <br>
  <router-link to="/myprofile/changepw">
  <button> 비밀번호 변경 페이지 </button>
  </router-link>
  <br>
  <router-link to="/help">
  <button> 문의하기 페이지 </button>
  </router-link>
  <br>
  <router-link to="/link">
  <button> 링크 공유 페이지 </button>
  </router-link>
  <br>
  <router-link to="/pet/list">
  <button> 동물 목록 페이지 </button>
  </router-link>
  <br>
  <router-link to="/version">
  <button> 버전 확인 페이지 </button>
  </router-link>
  <br>
  <BottomNavbar5/>
</template>

<script>
import BottomNavbar5 from '@/components/common/BottomNavbar5.vue';
import axios from "axios"

export default {
  name: "UserProfileView",
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
    BottomNavbar5
  },
  computed: {
  },
  methods: {
    LogOut() {
      this.$store.dispatch('logOut')
    },
    DeleteUser() {
      this.$store.dispatch('DeleteUser')
    },
    ChangeNickname() {
      this.$router.push({path:"/ChangeNickname"})
    },
    Invite() {
      const copyText = this.calendarId
      console.log(copyText)
      copyText.select();
      document.execCommand('copy');
      alert(copyText.value + '을 복사했습니다.')
    },
    getProfile() {
      axios({
          method: 'get',
          url: `http://70.12.247.124:8080/member/${localStorage.getItem("memberId")}`,
          headers: {Authorization: this.$store.state.accessToken},
        })
        .then((res) => {
          this.nickName = res.data.nickName
          this.calendarId = res.data.calendarId
          this.warnMemberList = res.data.warnMemberList
          this.blockMemberList = res.data.blockMemberList
          this.email = res.data.email
        })
        .catch((err) => {
              console.log(err)
              console.log(err.response.status)
              if (err.response.status === 401) {
                if (localStorage.getItem("token")) {
                  this.$store.dispatch('refresh', localStorage.getItem("token"))
                  this.dispatch('getProfile')
                }
              }
            })
      },
    },
  created() {
    this.getProfile()
  }
}
</script>

<style>

</style>