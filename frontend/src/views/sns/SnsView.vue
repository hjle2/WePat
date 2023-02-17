<template>
  <div v-if="flag1">
    <header class="">
      <div class="header-navbar">
        <button @click="flag1_()" type="button" class="btn btn-album-nav nav_orderby nav_orderby_new btn-a" id="new" style="font-size: 25px">최신순</button>
        <button @click="flag2_(1)" type="button" class="btn btn-album-nav nav_orderby nav_orderby_hot" id="month" style="font-size: 25px">오늘 👑</button>
        <button @click="flag3_(7)" type="button" class="btn btn-album-nav nav_orderby nav_orderby_hot" id="week" style="font-size: 25px">이주 👑</button>
        <button @click="flag4_(30)" type="button" class="btn btn-album-nav nav_orderby nav_orderby_hot" id="month" style="font-size: 25px">이달 👑</button>
      </div>
    </header>
  </div>
  <div v-if="flag2">
    <header class="">
      <div class="header-navbar">
        <button @click="flag1_()" type="button" class="btn btn-album-nav nav_orderby nav_orderby_new" id="new" style="font-size: 25px">최신순</button>
        <button @click="flag2_(1)" type="button" class="btn btn-album-nav nav_orderby nav_orderby_hot btn-a" id="month" style="font-size: 25px">오늘 👑</button>
        <button @click="flag3_(7)" type="button" class="btn btn-album-nav nav_orderby nav_orderby_hot" id="week" style="font-size: 25px">이주 👑</button>
        <button @click="flag4_(30)" type="button" class="btn btn-album-nav nav_orderby nav_orderby_hot" id="month" style="font-size: 25px">이달 👑</button>
      </div>
    </header>
  </div>
  <div v-if="flag3">
    <header class="">
      <div class="header-navbar">
        <button @click="flag1_()" type="button" class="btn btn-album-nav nav_orderby nav_orderby_new" id="new" style="font-size: 25px">최신순</button>
        <button @click="flag2_(1)" type="button" class="btn btn-album-nav nav_orderby nav_orderby_hot" id="month" style="font-size: 25px">오늘 👑</button>
        <button @click="flag3_(7)" type="button" class="btn btn-album-nav nav_orderby nav_orderby_hot btn-a" id="week" style="font-size: 25px">이주 👑</button>
        <button @click="flag4_(30)" type="button" class="btn btn-album-nav nav_orderby nav_orderby_hot" id="month" style="font-size: 25px">이달 👑</button>
      </div>
    </header>
  </div>
  <div v-if="flag4">
    <header class="">
      <div class="header-navbar">
        <button @click="flag1_()" type="button" class="btn btn-album-nav nav_orderby nav_orderby_new" id="new" style="font-size: 30px">최신순</button>
        <button @click="flag2_(1)" type="button" class="btn btn-album-nav nav_orderby nav_orderby_hot" id="month" style="font-size: 30px">오늘 👑</button>
        <button @click="flag3_(7)" type="button" class="btn btn-album-nav nav_orderby nav_orderby_hot" id="week" style="font-size: 30px">이주 👑</button>
        <button @click="flag4_(30)" type="button" class="btn btn-album-nav nav_orderby nav_orderby_hot btn-a" id="month" style="font-size: 30px">이달 👑</button>
      </div>
    </header>
  </div>
  
<div id="SnsView">
  <div class="holder-sns-nav">
    <SnsList1 v-if="flag1" :snss="snss1" />
    <SnsList2 v-if="flag2" :snss="snss2" />
    <SnsList3 v-if="flag3" :snss="snss3" />
    <SnsList4 v-if="flag4" :snss="snss4" />
    <!-- <SnsList v-if="flag" :snss="snss" /> -->
  </div>
  <BottomNavbar3 />
</div>
</template>


<script>
import SnsList1 from "@/components/sns/SnsList1.vue";
import SnsList2 from "@/components/sns/SnsList2.vue";
import SnsList3 from "@/components/sns/SnsList3.vue";
import SnsList4 from "@/components/sns/SnsList4.vue";
import BottomNavbar3 from "@/components/common/BottomNavbar3.vue";

export default {
  components: {
    SnsList1,
    SnsList2,
    SnsList3,
    SnsList4,
    BottomNavbar3,
  },
  data() {
    return {
      flag1: true,
      flag2: false,
      flag3: false,
    };
  },
  computed: {
    snss1() {
      return this.$store.state.snss1;
    },
    snss2() {
      return this.$store.state.snss2.slice(0, 10);
    },
    snss3() {
      return this.$store.state.snss3.slice(0, 10);
    },
    snss4() {
      return this.$store.state.snss4.slice(0, 10);
    },
  },
  created() {
    this.getSns1(0);
    this.getSns2(1);
    this.getSns3(7);
    this.getSns4(30);
  },
  methods: {
    getSns1(day) {
      this.$store.dispatch("getSns1", day);
    },
    getSns2(day) {
      this.$store.dispatch("getSns2", day);
    },
    getSns3(day) {
      this.$store.dispatch("getSns3", day);
    },
    getSns4(day) {
      this.$store.dispatch("getSns4", day);
    },
    flag1_() {
      this.flag1 = true;
      this.flag2 = false;
      this.flag3 = false;
      this.flag4 = false;
      this.getSns1(0);
    },
    flag2_(day) {
      this.flag1 = false;
      this.flag2 = true;
      this.flag3 = false;
      this.flag4 = false;
      this.getSns2(day);
    },
    flag3_(day) {
      this.flag1 = false;
      this.flag2 = false;
      this.flag3 = true;
      this.flag4 = false;
      this.getSns3(day);
    },
    flag4_(day) {
      this.flag1 = false;
      this.flag2 = false;
      this.flag3 = false;
      this.flag4 = true;
      this.getSns4(day);
    },
  },
};
</script>


<style lang="scss">
#button {
  width: 100vw !important;
  display: flex;
  // margin-right: 50vw;
}
.btn-a {
  background: $color-main !important;
  color: white !important;
}
.btn-album-nav {
  // border-radius: 0 !important;
  border-end-start-radius: 0 !important;
  border-end-end-radius: 0 !important;
}
.row {
  // width: 100vw;
  // display: flex;
}
.holder-sns-nav {
  margin-top: 50px !important;
}
#new {
  width: 25vw;
}
#today {
  width: 25vw;
}
#week {
  width: 25vw;
  // background-color: $color-main-75;
}
#month {
  width: 25vw;
}
</style>