<template>
    <div v-for="sns in snss" :key="sns.id" :sns="sns">
      <SnsListItem :sns="sns" />
    </div>
  </template>
  
  
  <script>
  import SnsListItem from "./SnsListItem.vue";
  
  export default {
    name: "SnsList3",
    data() {
      return {};
    },
    props: {
      snss: Object,
    },
    computed: {},
    components: {
      SnsListItem,
    },
  };
  </script>
  
  
  <style></style>