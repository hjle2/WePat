<template>
  <div class="holder-vanilla-op">
    <!-- <router-link to="/user">
      <img src="@/assets/back.png" class="logo-back" alt="" />
    </router-link> -->
    <p class="font-small font-grey"> 터치하면 상세 페이지로 이동합니다. </p>
    <div class="holder-category">
    </div>
    <br><br>
    <div class="">
      <PetIcon :pets="pets"/>
    </div>
  </div>
  <BottomNavbar5 />
</template>

<script>
import PetIcon from "@/components/mainpage/PetIcon.vue";
import BottomNavbar5 from "@/components/common/BottomNavbar5.vue"


export default {
  data() {
    return {
      pets : [],
    }
  },
  computed: {
  },
  components: {
    PetIcon,
    BottomNavbar5
  },
  created() {
    this.getPet()
  },
  methods: {
    getPet() {
      const dataSet =this.$store.dispatch("getPet2")
      dataSet.then((getData)=>{
        this.pets=getData

        return getData
      })
    },
  }
}
</script>

<style>

</style>