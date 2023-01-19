<template>
  <p>앨범</p>
  <router-link to="/album/detail">
  <button> 사진 상세페이지 </button>
  </router-link>
  <br>
  <BottomNavbar4/>
</template>

<script>
import BottomNavbar4 from '@/components/common/BottomNavbar4.vue';

export default {
  components : {
    BottomNavbar4
  }
}
</script>

<style>

</style>