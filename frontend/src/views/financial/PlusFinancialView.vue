<template>
<div class="holder-vanilla-op">
  <router-link  to="/financial">
      <img src="@/assets/back.png" class="logo-back" alt="">
    </router-link>
    <br>
  <div class="">
    <span class="font-small text-edit ">카테고리</span><br>
      <select class="input input-edit font-small" v-model="category">
        <option disabled value="">카테고리를 선택해 주세요</option>
        <option class="font-small">정기예금</option>
        <option class="font-small">보험비</option>
        <option class="font-small">맘마</option>
        <option class="font-small">까까</option>
        <option class="font-small">미용</option>
    <option>병원</option>
    <option>장난감</option>
    <option>그 외</option>
    </select>
    <div class="select">
     <input type="radio" id="select" name="shop" v-model="income" value="true" ><label for="select">수입</label>
     <input type="radio" id="select2" name="shop" v-model="income" value="false" ><label for="select2">지출</label>
    </div>
    <span class="font-small text-edit ">날짜</span><br>
    <input class="input input-edit font-small" type="date" v-model="date">
  <br>
    <!-- <p><span class="col-4">수입/지출</span><br> <input class="input col-5" type="3ext" v-model="income"></p> -->
    
    <span class="font-small text-edit">금액</span><br>
    <div>
      <input class="input input-edit-money text-end" type="number" v-model="money">
      <span class="col-1">  원 </span><br>
    </div>
    <span class="font-small text-edit">내역</span><br>
    <input class="input input-edit font-small" type="text" v-model="memo"><br>
      <br>
      <button class="btn-colored btn-medium input-btn" @keyup="enter" @click="addFinance()">추가하기</button>
  </div>
  <br>
</div>
  <BottomNavbar2/>
</template>

<script>
import BottomNavbar2 from '@/components/common/BottomNavbar2.vue';

export default {
  components : {
    BottomNavbar2
  },
  data: function () {
    return {
    category :"",
    date:"",
    income: true,
    memo:"",
    money: 0,
    }
  },
  watch: {
    category: function () {
      if (this.category === "정기예금" || this.category === "보험비") {
        this.income = true
      } else {
        this.income = false
      }
    // this.getThisMonthData()
    }
  },
  methods:{
    addFinance() {
        const category = this.category
        const date = this.date
        const income = this.income
        const memo = this. memo
        const money = this.money

        const payload = {
          category, date, income, memo, money}
        // console.log(payload)
        this.$store.dispatch('addFinance', payload)
      }
    
    },
  created() {
    let date = new Date();
    let year = date.getFullYear();
    let month = date.getMonth();
    month += 1;
    if (month <= 9){
        month = "0" + month;
    }
    let day = date.getDate();
    if (day <= 9){
        day = "0" + day;
    }
    const today = year + '-' + month + '-' + day;
    console.log(today);
    this.date = today
    },
}
</script>

<style lang="scss">
.text-edit {
  // text-align: left !important;
  margin-left: -45vw !important;
  // left: 0vw !important;
}
.input-edit {
  width: 60vw !important;
  height: 7vh;
  border-radius: $radius-size-2;
  border: 1vh solid $color-main-75;
  margin: 0;
  padding-left: 5vw;
}

.input-edit-money {
  width: 55vw !important;
  height: 7vh;
  border-radius: $radius-size-2;
  border: 1vh solid $color-main-75;
  margin: 0;
  padding-left: 5vw;
}

.input-btn {
  width: 60vw !important;
  height: 7vh;
  border-radius: $radius-size-2;
  border: 1vh solid $color-main-75;
  margin: 0;
  // padding-left: 5vw;
}
</style>