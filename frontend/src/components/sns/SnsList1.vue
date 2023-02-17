<template>
    <div v-for="sns in snss" :key="sns.id" class="sns-float" :sns="sns">
      <SnsListItem1 :sns="sns" />
    </div>
  </template>
  
  
  <script>
  import SnsListItem1 from "./SnsListItem1.vue";
  
  export default {
    name: "SnsList1",
    data() {
      return {};
    },
    props: {
      snss: Object,
    },
    computed: {},
    components: {
      SnsListItem1,
    },
  };
  </script>
  
  
<style lang="scss">
.sns-float {
  float: left;
  margin-left: 1vw;
  margin-top: 1vw;
  margin-right: 1.5vw;
  width: 22vw;
}
</style>