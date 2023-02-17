<template>
  <div class="pet-margin">
    <img class="profile-image-small" :src="pet.photoUrl" :style="`border: ${pet.color} solid 0.7vh`" @click="detailPet(pet.petId)"/>
  </div>
</template>

<script>
import PetProfileView from '@/views/pet/PetProfileView.vue';

export default {
  name: 'PetIconItem',
  data() {
    return {
    }
  },
  created() {
  },
  props: {
    pet : Object,
  },
  methods: {
    detailPet(petId) {
      this.$router.push({name:PetProfileView, params:{id:petId}})
    },
  },
}
</script>

<style lang="scss">
</style>