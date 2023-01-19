<template>
  <p>SNS</p>
  <router-link to="/sns/detail">
  <button> SNS사진 상세페이지 </button>
  </router-link>
  <br>
  <BottomNavbar3/>
</template>

<script>
import BottomNavbar3 from '@/components/common/BottomNavbar3.vue';

export default {
  components : {
    BottomNavbar3
  }
}
</script>

<style>

</style>