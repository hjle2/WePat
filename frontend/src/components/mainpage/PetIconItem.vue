<template>
  <div class="flip">
    <div class="pet-div-position name-card">
      <div class="name-front">
        <img src="@/assets/nametag/front.png" alt="" class="nametag-huge" />
        <img class="profile-image-huge" :src="pet.photoUrl" />
      </div>
      <div class="name-back">
        <img
          src="@/assets/nametag/back.png"
          alt=""
          class="nametag-huge"
          @click="detailPet(pet.petId)"
        />
        <div class="font-medium name-font" @click="detailPet(pet.petId)">
          <p>이름 : {{ pet.name }}</p>
          <p>품종 : {{ pet.types }}</p>
          <p>나이 : {{ age }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PetProfileView from "@/views/pet/PetProfileView.vue"

export default {
  name: "PetIconItem",
  data() {
    return {
      age: 0,
      date: "",
    }
  },
  created() {
    let today = new Date()
    let year = today.getFullYear()
    this.date = year
    this.age =
      parseInt(this.date) - parseInt(this.pet.birthday.substring(0, 4)) + 1
  },
  props: {
    pet: Object,
  },
  methods: {
    detailPet(petId) {
      this.$router.push({ name: PetProfileView, params: { id: petId } })
    },
  },
}
</script>

<style lang="scss"></style>
