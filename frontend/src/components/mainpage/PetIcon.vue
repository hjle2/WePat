<template>
  <div v-for="pet in pets" :key="pet.id" :pet="pet">
    <PetIconItem :pet="pet"/>
  </div>
</template>

<script>
import PetIconItem from './PetIconItem.vue';

export default {
  computed: {
    pets() {
      return this.$store.state.pets
    }
  },
    components: {
        PetIconItem,
    }

}
</script>

<style>

</style>