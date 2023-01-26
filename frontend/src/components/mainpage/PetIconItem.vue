<template>
  <div>
    <p @click="petDetail(pet.id)">펫이미지</p>
  </div>
</template>

<script>
import PetProfileView from '@/views/petprofile/PetProfileView.vue';

export default {
  name: 'PetIconItem',
  data() {
    return {

    }
  },
  props: {
    pet : Object,
  },
  methods: {
    petDetail(petId) {
      this.$router.push({name:PetProfileView, params:{id:petId}})
    }
  }
}
</script>

<style>

</style>