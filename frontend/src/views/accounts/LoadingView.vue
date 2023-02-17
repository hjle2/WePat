<template>
<div class="loading-back" v-if="this.$store.state.loadingStatus === true">
  <div class="loading-icon" v-if="this.$store.state.loadingStatus === true">
    <img :src="require(`@/assets/patting/pat${num}.gif`)" alt="">
    <p class="loading">NOW LOADING...</p>
    <!-- <p>{{ loading  }}</p> -->
  </div>
</div>
</template>

<script>
import _ from 'lodash'

export default {
  name: 'StartView',
  data() {
    return {
      num: null,
      // loading:null,
    }
  },
  // props: {
  //     loading: {
  //       type: Boolean,
  //       required: true,
  //     },
  //   },
  // watch: {
  //   loading: function () {
  //     this.getBack()
  //   }
  // },
  methods: {
    getBack() {
        this.num = _.sample(_.range(1, 32))
    }
  },
  created() {
    this.getBack()
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss">

.loading-back {
  top: 0;
  left: 0;
  background-color: #FEF4EB;
  // background-color: #797979c3;
  height: 100vh;
  width: 100vw;
  z-index: 9;
  position: fixed;
}

.loading-icon {
  height: 40vh;
  width: 40vh;
  overflow: hidden;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 10;
  position: fixed;
  vertical-align: center;
  border-radius: 70%;
  border: $color-main solid 2vh;
  background-color: #FEF4EB;
  }

.loading-icon > img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  top: 50%;
  left: 50%;
  // transform: translate(-50%, -50%);
}

.loading {
  position: absolute;
  top: 5%;
  left: 8%;
  // text-align: center;
  font-size: 9vh;
  color: $color-main;
  font-weight: bolder;
  // text-shadow: -2px 0 black, 2px 0 black, 2px 0 black, -2px 0 black;
  -webkit-text-stroke: 1px black;
}
</style>
