<template>
  <!-- {{ pet }} -->
  <div class="holder-vanilla-op">
    <img src="@/assets/back.png" @click="goBack" class="logo-back" alt="" />
    <br />
    <div class="holder-pet">
      <!-- <br> -->
      <div class="holder-profile">
        <!-- <br> -->
        <img
          class="profile-image-huge"
          :src="pet.photoUrl"
          :style="`border: ${pet.color} solid 1vh`"
        />
      </div>
      <div class="holder-category"></div>
      <div class="">
        <p class="font-medium font-no-margin" v-if="pet.birthday">
          {{ pet.name }}({{ pet.types }}, {{ age }}살)
        </p>
        <p class="font-medium font-no-margin" v-if="!pet.birthday">
          {{ pet.name }}({{ pet.types }})
        </p>
        <p class="font-small font-no-margin" v-if="pet.birthday">
          태어난 날 : {{ pet.birthday }}
        </p>
        <p class="font-small font-no-margin" v-if="pet.adaptday">
          가족이 된 날 : {{ pet.adaptday }}
        </p>
      </div>
    </div>
    <div class="holder-category"></div>
    <div>
      <!-- <div class="weight-input-position">
        <span class="">어제까지의 몸무게</span>
        <span> 00kg </span>
      </div> -->
      <div class="weight-input-position">
        <span class="">오늘의 몸무게</span>
        <div>
          <input class="weight-input" v-model="weight" />
          <button
            class="btn-colored-no-shadow btn-weight font-large"
            @click="addPetWeight()"
          >
            ↵
          </button>
        </div>
      </div>
    </div>
    <div class="holder-category"></div>
    <!-- <p class="psize3">이번주 일정~~~</p> -->
    <div class="holder-weight">
      <PetWeightList :pet="pet" ref="PetWeightList" />
    </div>
    <br />
    <br /><br />
    <div class="holder-pet-detail">
      <button
        class="btn-large btn-colored at-bottom"
        @click="updatePet(this.pet.petId)"
      >
        수정
      </button>
      <button class="btn-large btn-colored at-bottom" @click="popOpenDelete()">
        삭제
      </button>
    </div>
    <br />
  </div>
</template>

<script>
import PetUpdateView from "@/views/pet/PetUpdateView.vue"
import PetWeightList from "@/components/common/PetWeightList.vue"
import Swal from "sweetalert2"

export default {
  name: "PetProfileView",
  data() {
    return {
      weight: "",
      date: "",
      age: 0,
    }
  },
  computed: {
    pet() {
      return this.$store.state.pet
    },
  },
  components: {
    PetWeightList,
  },
  updated() {
    this.age =
      parseInt(this.date.substring(0, 4)) -
      parseInt(this.pet.birthday.substring(0, 4)) +
      1
  },
  created() {
    let today = new Date()
    let year = today.getFullYear() // 년도
    let month = today.getMonth() + 1 // 월
    let date = today.getDate() // 날짜

    this.date =
      year + (month < 9 ? "0" + month : month) + (date < 9 ? "0" + date : date)
    this.detailPet()
  },
  // beforeUpdate() {
  //   this.download()
  // },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    detailPet() {
      this.$store.dispatch("detailPet", this.$route.params.id)
    },
    addPetWeight() {
      const date = this.date
      const weight = this.weight
      const petId = this.$route.params.id

      const payload = {
        date,
        weight,
        petId,
      }

      this.$store.dispatch("addPetWeight", payload)
      this.weight = ""
      // console.log(this.$refs)
      // this.$refs.PetWeightList.newChart()
    },
    updatePet(petId) {
      this.$router.push({ name: PetUpdateView, params: { id: petId } })
    },
    popOpenDelete() {
      Swal.fire({
        title: "기록 삭제",
        text: "정말로 삭제하시겠습니까?",
        icon: "warning",

        showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
        confirmButtonColor: "#faa960", // confrim 버튼 색깔 지정
        cancelButtonColor: "#d33", // cancel 버튼 색깔 지정
        confirmButtonText: "예", // confirm 버튼 텍스트 지정
        cancelButtonText: "아니오", // cancel 버튼 텍스트 지정

        reverseButtons: true, // 버튼 순서 거꾸로
      }).then((result) => {
        if (result.isConfirmed) {
          this.$store.dispatch("deletePet", this.pet.petId)
        }
      })
    },
  },
}
</script>

<style lang="scss">
.psize3 {
  font-size: $font-size-3;
}
.weight-input-position {
  width: 90%;
  height: 7.2vh;
  // bottom: 0rem !important;
  // left: -0.2rem !important;
  position: relative;
  display: flex;
  justify-content: space-between;
  // margin-left: auto;
  // margin-right: auto;
}

.btn-weight {
  //height: 100%;
  position: absolute;
  top: 0%;
  height: 7.2vh;
  width: 10vw;
  border: 0.5vh solid $color-main-75 !important;
}
.weight-input {
  // float: left;
  font-size: $font-size-1;
  color: white;
  // outline: none;
  background-color: rgb(238, 213, 184);
  width: 25vw;
  height: 100%;
  border-radius: 0 !important;
  border: 5px solid $color-main-75;
  margin: 0;
  // padding-left: 5vw;
  // z-index: 2 !important;
}

.holder-weight {
  width: 90vw !important;
}

.holder-pet {
  // border: $border-size-3 solid $color-main-75;
  // border-left: none;
  // border-right: none;
  background: #fef4eb;
  padding: 0.5vw;
  padding-left: 5vw;
  padding-right: 5vw;
  width: 100%;
  height: 45vh;
  margin: 0vh;
  // position: sticky !important;
  top: 0;
  // box-shadow: inset 0.3rem 0.1rem 0.5rem $color-main-50;
  border-radius: none;
  overflow: hidden;
  font-size: $font-size-4;
  z-index: 1;
  display: block;
  // justify-content: space-between;
}
.holder-pet-detail {
  display: flex;
  justify-content: space-between;
}
</style>
