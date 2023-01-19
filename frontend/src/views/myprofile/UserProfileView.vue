<template>
  <p>프로필</p>
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

export default {
  components : {
    BottomNavbar5
  }
}
</script>

<style>

</style>