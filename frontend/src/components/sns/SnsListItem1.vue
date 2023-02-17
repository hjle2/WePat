<template>
  <div class="div-sns">
    <img :src='sns.photoUrl' @click="snsDetail(sns.photoId)" class="img-sns" />
    <!-- height="200" width="167" -->
  </div>
</template>


<script>
import DetailSnsView from "@/views/sns/DetailSnsView.vue";

export default {
  name: "SnsListItem1",
  data() {
    return {};
  },
  created() {
  },
  props: {
    sns: Object,
  },
  methods: {
    snsDetail(photoId) {
      this.$router.push({ name: DetailSnsView, params: { id: photoId } });
    },
  },
};
</script>


<style lang="scss">
.div-sns {
  // position: absolute;
  // right: 60px;
  // font-size: $font-size-3;
  width: 24vw;
  height: 15vh;
  border-radius: 30px;
  border: 1px solid #ccc;
  //   position: relative;
}
.img-sns {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 30px;
}
</style>