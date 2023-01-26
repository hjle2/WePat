<template>
  <p>{{ pet.name }}</p>
  <p>{{ pet.adaptday }}</p>
  <button @click="petUpdate(this.pet.id)">수정</button>
  <button @click="petDelete()">삭제</button>
</template>

<script>
import PetUpdateView from "@/views/petprofile/PetUpdateView.vue"
import axios from 'axios';
const API_URL = 'http://70.12.247.105:8080'

export default {
  name: 'PetProfileView',
  data() {
    return {
      pet: [],
    }
  },
  created() {
    this.getPetDetail()
  },
  methods: {
    getPetDetail() {
      axios({
        method: 'get',
        url: `${API_URL}/pet/${this.$route.params.id}`,
      })
      .then( res => this.pet = res.data )
      .catch( err => console.log(err))
    },
    petUpdate(petId) {
      this.$router.push({name:PetUpdateView, params:{id:petId}})
    },
    petDelete() {
      axios({
        method: 'delete',
        url: `${API_URL}/pet/${this.pet.calendarid}/${this.pet.id}`
      })
      .then( res => console.log(res) )
      .catch( err => console.log(err) )
    }
  }
}
</script>

<style>

</style>