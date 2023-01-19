<template>
  <p>가계부</p>
  <br>
  <router-link to="/financial/add">
    <button> 수입/지출내역 추가페이지 </button>
    </router-link>
    <br>
  <BottomNavbar2/>
</template>

<script>
import BottomNavbar2 from '@/components/common/BottomNavbar2.vue';

export default {
  components : {
    BottomNavbar2
  }
}
</script>

<style>

</style>