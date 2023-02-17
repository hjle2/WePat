<template>
<div id="FinancialView">
    <div class="holder-finance">
      <!-- 총액 -->
      <div class="text-end">
        <p class="font-small font-no-margin">총계</p>
        <p class="font-large font-no-margin">{{ total.toLocaleString() }}원</p>
      </div>
      <div class="holder-category">
          <p class="font-lefts"></p>
        </div>
      <div class="">
        <span class="" @click="month--">&lt; &nbsp; &nbsp; &nbsp;</span>
        <span class="font-small">{{ year }}년 {{ month }}월</span>
        <span class="" @click="month++">&nbsp; &nbsp; &nbsp; &nbsp; ></span>
      </div>
      <div class="box-finance">
        <div class="">
          <p class="font-small font-no-margin">수입</p>
          <p class="font-medium font-no-margin ft-green" id="income">{{ sumIncome.toLocaleString() }}원</p>
        </div>
        <div class="">
          <p class="font-small font-no-margin">지출</p>
          <p class="font-medium font-no-margin ft-red" id="spend">{{ sumSpend.toLocaleString() }}원</p>
        </div>
        <div class="">
          <p class="font-small font-no-margin">소계</p>
          <p class="font-medium font-no-margin ft-blue" id="total">{{ (sumIncome - sumSpend).toLocaleString() }}원</p>
        </div>
      </div>
    </div>
  <div class="holder-vanilla-financial">
    <router-link  to="/financial/add">
      <img src="@/assets/add.png" class="add-img" alt="">
    </router-link>

    <!-- <p>{{ date.month }}</p>
  <month-picker @change="showDate"></month-picker> -->

    <!-- 이번 달 수입 지출 계 -->
    <!-- 이번 달 수입/지출 내역(클릭 시 상세페이지로 이동) -->
    <!-- <p>{{ FinanceList }}</p> -->
    <div v-for="finance in FinanceList" :key="finance.date" :finance="finance" @click="goDetail(finance.financeId, finance.nickName)">
      
      <div v-show="Number(finance.date.substr(0, 4)) === year &&  Number(finance.date.substr(5, 2)) === month">

        <div v-if="finance.income == true" class="holder-white-medium-income box-finance">
          <div class="text-start">
            <span class="font-small font-no-margin">{{ finance.date }}</span> <br>
            <span class="font-small font-no-margin">{{ finance.memo }} </span> <span class="font-grey font-small font-no-margin">#{{ finance.category }}</span>
            <!-- <p class="font-small">{{ finance.nickName }}</p> -->
          </div>
          <div>
            <span class="font-money ft-green"> {{ finance.money.toLocaleString() }}</span>
            <span>&nbsp; &nbsp; </span>
          </div>
        </div>
        <div v-else class="holder-white-medium-spend box-finance">
          <div class="text-start">
            <span class="font-small font-no-margin">{{ finance.date }}</span> <br>
            <span class="font-small font-no-margin">{{ finance.memo }} </span> <span class="font-grey font-small font-no-margin">#{{ finance.category }}</span>
            <!-- <p class="font-small">{{ finance.nickName }}</p> -->
          </div>
          <div>
            <span class="font-money ft-red"> {{ finance.money.toLocaleString() }}</span>
            <span>&nbsp; &nbsp; </span>
          </div>
        </div>
      </div>
    </div>
  </div>
  <BottomNavbar2/>
  </div>
</template>

<script>
import BottomNavbar2 from '@/components/common/BottomNavbar2.vue';
import FinanceDetailView from "@/views/financial/FinanceDetail.vue"

export default {
  data() {
    return {
      today : null,
      FinanceList : null,
      sumIncome: 0,
      sumSpend: 0,
      year: null,
      month: null,
      startYear: null,
      startMonth: null,
      total: 0,
      financeId: null,
      nickName: null,
      calendarId : null,
        }
  },
  components : {
    BottomNavbar2,
    // MonthPicker,
  },
   watch: {
    month: function () {
      if (this.month === 0) {
        this.year--
        this.month = 12
      } else if (this.month === 13) {
        this.year++
        this.month = 1
      }
    this.getThisMonthData()
    }
  },
  methods : {
    getUserData() {
      const dataSet = this.$store.dispatch('getUserData')
      dataSet.then((getData) => {
        this.nickName = getData.nickName
        this.calendarId = getData.calendarId
        console.log("데이터 가져옴")
        console.log(this.nickName)
        return getData.nickName
      })
    },

    setMonth() {
      let today = new Date()
      this.year = today.getFullYear()
      this.month = today.getMonth() + 1
      // console.log(today + this.year + this.month)
    },
    getFinanceList() {
      const dataSet = this.$store.dispatch('getFinanceData')
      dataSet.then((getData) => {
        this.FinanceList = getData.sort((a, b) => {
          return new Date(a.date).getTime() - new Date(b.date).getTime()}).reverse()
          
          // const startDay = (this.FinanceList[this.FinanceList.length - 1].date)
          // // console.log("시작은" + startDay)
          // this.startYear = startDay.substr(0, 4)
          // this.startMonth = startDay.substr(5, 2)
        
        // console.log(getData)
        for (let unit of getData) {
          if (unit.income === true) {
            this.total += unit.money
          } else {
            this.total -= unit.money
          }
        }
        this.getThisMonthData()
        return getData
      })
    },  
    getThisMonthData () {
      this.sumIncome = 0
      this.sumSpend = 0
      for (let unit of this.FinanceList) {
        if (Number(unit.date.substr(0, 4)) === this.year && Number(unit.date.substr(5, 2)) === this.month) {
            if (unit.income === true) {
              this.sumIncome += unit.money
            } else {
              this.sumSpend += unit.money
            }
          }
      }
    },
    goDetail(financeId, nickName) {
      console.log("받은 id" + financeId)
      this.$router.push({name:FinanceDetailView, params:{financeId : financeId, calendarId : this.calendarId, nickName : nickName}})
    }
  },
  created() {
    this.setMonth()
    this.getFinanceList()
    this.getUserData()
    },
}
</script>

<style>
.box-finance {
  display: flex;
  justify-content: space-between;
}
</style>