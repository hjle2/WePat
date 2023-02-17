<template>
  <div class="holder-vanilla-op">
    <router-link  to="/financial">
        <img src="@/assets/back.png" class="logo-back" alt="">
      </router-link>
    <p class="font-small text-end">작성자: {{ nickName }}</p>
    <div class="">
      <span class="font-small text-edit">카테고리</span><br>
        <select class="input input-edit font-small" v-model="category" placeholder="category">
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
      <span class="font-small text-edit">날짜</span><br>
      <input class="input input-edit" type="date" v-model="date" placeholder="data">
    <br>
      <!-- <p><span class="col-4">수입/지출</span> <input class="input input-edit col-5" type="3ext" v-model="income"></p> -->
      <span class="font-small text-edit">금액</span><br>
      <div>
        <input class="input input-edit-money text-end" type="number" v-model="money" placeholder="money">
        <span class="col-1">  원 </span>
      </div>
      <span class="font-small text-edit">내역</span><br>
      <input class="input input-edit" type="text" v-model="memo" placeholder="memo"><br><br>
      <button v-show="nickName === UserNickName" class="btn-colored btn-medium input-btn" @click="changeFinance">수정하기</button><br>
      <button v-show="nickName === UserNickName" class="btn-colored btn-medium input-btn" @click="deleteFinance">삭제하기</button>
      <br>
</div>
</div>
</template>

<script>
export default {
    name: "FinanceDetailView",
    data() {
      return {
        calendarId: null,
        financeId: null,
        category :"",
        date:"",
        income: true,
        memo:"",
        money: 0,
        nickName: "",
        UserNickName: "",
      }
    },
  components : {
  },
  methods : {

    getFinanceData() {
      const dataSet = this.$store.dispatch('detailFinance', this.financeId)
      dataSet.then((getData) => {
        console.log(getData)

        this.category = getData.category
        this.date = getData.date
        this.income = getData.income
        this.memo = getData.memo
        this.money = getData.money

        console.log("가져오기 완료")
        return getData
      })
    },

    changeFinance() {
      const calendarId = this.calendarId
      const category = this.category
      const date = this.date
      const financeId = this.financeId
      const income = this.income
      const memo = this.memo
      const money = this.money
      const nickName = this.nickName
      const payload = { calendarId, category, date, financeId, income, memo, money, nickName }
      console.log("수정보낼거")
      console.log(payload)
      this.$store.dispatch('changeFinance', payload)
      // this.$router.push({ path: '/financial' })
    },

  deleteFinance() {
    const financeId = this.financeId
    const calendarId = this.calendarId
    const payload = { financeId, calendarId } 
    console.log("삭제보낼거")
    console.log(payload)
    this.$store.dispatch('deleteFinance', payload)
    // this.$router.push({ path: '/financial' })
  },

  getUserData() {
      const dataSet = this.$store.dispatch('getUserData')
      dataSet.then((getData) => {
        this.UserNickName = getData.nickName
        console.log("데이터 가져옴")
        console.log(this.UserNickName)
        return getData.nickName
      })
    },

},
  created() {
    console.log(this.$route.params)
    this.financeId = this.$route.params.financeId
    this.nickName = this.$route.params.nickName
    this.calendarId = this.$route.params.calendarId
    console.log("파라미터로 가져온 것")
    console.log(this.financeId)
    console.log(this.nickName)
    console.log(this.calendarId)
    this.getFinanceData()
    this.getUserData()
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