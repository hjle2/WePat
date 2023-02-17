<template>
  <div v-for="pet in pets" :key="pet.id" :pet="pet" class="pet-float-center">
    <!-- <img src="@/assets/nametag/front.png" alt="" class="nametag-huge"> -->
    <PetIconItem :pet="pet" /><br />
  </div>
</template>

<script>
import PetIconItem from "./PetIconItem.vue"

export default {
  props: {
    pets: Object,
  },
  computed: {},
  components: {
    PetIconItem,
  },
}
</script>

<style></style>
