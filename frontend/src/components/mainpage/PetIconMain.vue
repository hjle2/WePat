<template>
  <div v-for="pet in pets" :key="pet.id" :pet="pet" class="pet-float-left at-bottom ">
    <PetIconItemMain :pet="pet"/>
  </div>
</template>

<script>
import PetIconItemMain from './PetIconItemMain.vue';

export default {
  props: {
    pets: Object,
  },
  computed: {
  },
    components: {
        PetIconItemMain,
    }

}
</script>

<style>

</style>