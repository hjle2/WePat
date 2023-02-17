<template>
  <div class="sns-border">
    <div class="sns-margin">
      <img :src='sns.photoUrl' class="image" />
      <div class="holder-sns">
        <img src="@/assets/declaration.png" class="declaration" @click="goDeclaration(sns.photoId)" />
        <div>
          <img src="@/assets/heart.png" class="heart" @click="goLike(sns.photoId)" />
          <span v-if="!sns1" class="p-right">{{ sns.like }}</span>
          <span v-if="sns1" class="p-right">{{ sns1 }}</span>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import DetailSnsView from "@/views/sns/DetailSnsView.vue";
import Swal from "sweetalert2";

export default {
  name: "SnsListItem",
  data() {
    return {
      sns1: "",
      reportPhotoId: "",
      sns_like :"",
    };
  },
  created() {
    this.detailSns();
  },
  props: {
    sns: Object,
  },
  methods: {
    goDeclaration(payload) {
      console.log(payload);
      Swal.fire({
        title: "게시물 신고",
        text: "이 게시물을 신고하시겠습니까?",
        icon: "warning",

        showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
        confirmButtonColor: "#faa960", // confrim 버튼 색깔 지정
        cancelButtonColor: "#d33", // cancel 버튼 색깔 지정
        confirmButtonText: "예", // confirm 버튼 텍스트 지정
        cancelButtonText: "아니오", // cancel 버튼 텍스트 지정

        reverseButtons: true, // 버튼 순서 거꾸로
      }).then((result) => {
        if (result.isConfirmed) {
          console.log(result);
          console.log(payload);
          this.$store.dispatch("goDeclaration", payload);
        }
      });
    },
    detailSns() {
      this.$store.dispatch("detailSns", this.$route.params.id);
    },
    goLike(photoId) {
      const dataSet = this.$store.dispatch("goLike", photoId);
      dataSet.then((getData) => {
        this.sns1 = getData;
        console.log(this.sns1);
        return getData;
      });
    },
    snsDetail(photoId) {
      this.$router.push({ name: DetailSnsView, params: { id: photoId } });
    },
  },
};
</script>


<style lang="scss">
.sns-border {
  margin-top: 1vh;
  width: 100vw;
  position: relative;
}
.p-right {
  // margin-left: 60vw;
  font-size: $font-size-3;
}
.image {
  height: auto;
  width: 100vw;
}
.heart {
  margin: 2vw;
  width: 4.5vh;
  height: 4.5vh;
}
.sns-like {
  font-size: 80px;
}
img {
  float: left;
}
.modal-bg {
  width: 50px;
  position: fixed;
  bottom: 120px;
  right: 30px;
  z-index: 1;
}
.holder-sns {
  width: 100vw;
  height: 6vh;
  position: relative;
  padding-bottom: 1vh;
  padding-right: 30px;
  display: flex;
  justify-content: space-between;
  border-bottom: 0.3rem solid $color-main-25;
}
.declaration {
  margin: 2vw;
  margin-top: 1.8vh;
  height: 3vh;
  width: 3vh;
}
</style>

