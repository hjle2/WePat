<template>
  <div class="hello" @touchstart="swipeStart" @touchend="swipeEnd">
    <canvas class="set" id="myChart"></canvas>
  </div>
  <div>
    <p>마지막에 잰 몸무게</p>
    <div>
      <span v-if="pet.weightList.length>0">{{ pet.weightList[pet.weightList.length - 1].date.slice(2,4) + "년" + " " + pet.weightList[pet.weightList.length - 1].date.slice(4,6) + "월" + " " + pet.weightList[pet.weightList.length - 1].date.slice(6,8) + "일"}} : </span>
      <span v-if="pet.weightList.length>0">{{ pet.weightList[pet.weightList.length - 1].weight + "Kg"}}</span>
    </div>
  </div>
</template>


<script>
import Chart from "chart.js/auto";
let myChart;
export default {
  name: "PetWeightList",
  data() {
    return {
      touchStartX: 0,
      touchStartY: 0,
      start: 0,
      end: 0,
      chartMax: 0,
      // chartjsLabel: ["230101", "230110", "230115", "230127", "230201", "230202", "230202", "230202", "230202", "230202", "230202", "230202", "230202", "230202"],
      // chartjsData: ["1", "2", "4", "5", "8", "10", "10", "10", "10", "10", "10", "10", "10", "10"],
    };
  },
  props: {
    pet: Object,
  },
  methods: {
    swipeStart(event) {
      // console.log(event.touches[0]);
      this.touchStartX = event.touches[0].clientX;
      this.touchStartY = event.touches[0].clientY;
    },
    swipeEnd(event) {
      if (event.touches.length == 0) {
        var touch = event.changedTouches[event.changedTouches.length - 1];
        // console.log(touch);
        const touchEndX = touch.clientX;
        const touchEndY = touch.clientY;
        if ((touchEndX - this.touchStartX >= 80 || this.touchStartX - touchEndX >= 80) && (touchEndY - this.touchStartY <= 10 || this.touchStartY - touchEndY <= 10)) {
          if (touchEndX - this.touchStartX > 0) this.moveRight();
          else this.moveLeft();
        }
      }
    },
    moveLeft() {
      console.log("MOVED LEFT");
      this.end = Math.min(this.pet.weightList.length, this.end + 2);
      this.start = Math.max(0, this.end - 10);
      this.newChart();
    },
    moveRight() {
      console.log("MOVED RIGHT");
      this.start = Math.max(0, this.start - 2);
      this.end = Math.min(this.pet.weightList.length, this.start + 10);
      this.newChart();
    },
    newChart() {
      myChart.destroy();
      const weights = [];
      const dates = [];
      for (var i = this.start; i < this.end; i++) {
        weights.push(this.pet.weightList[i].weight);
        let date = this.pet.weightList[i].date.substring(2, 4) + "." + this.pet.weightList[i].date.substring(4, 6) + "." + this.pet.weightList[i].date.substring(6, 8);
        dates.push(date);
      }
      const ctx = document.getElementById("myChart");

      myChart = new Chart(ctx, {
        type: "line",
        data: {
          labels: dates,
          // labels: this.chartjsLabel,
          datasets: [
            {
              label: this.pet.name + " 몸무게",
              pointBackgroundColor: "orange",
              data: weights,
              // data: this.chartjsData,
              backgroundColor: ["rgba(255, 165, 0)"],
              borderColor: ["rgba(255, 165, 0)"],
              borderWidth: 5, //선 굵기
            },
          ],
        },
        options: {
          plugins: {
            legend: {
              display: false, //라벨
              position: "top",
              labels: {
                boxWidth: 0, //동그라미
                padding: 0,
                usePointStyle: true,
                pointStyle: "circle",
                font: {
                  size: 10,
                },
              },
              fullSize: true,
              align: "center",
            },
          },
          responsive: false,
          scales: {
            x: {
              stacked: false,
              display: true,
            },
            y: {
              beginAtZero: true,
              min: 0,
              max: this.chartMax,
              ticks: {
                stepSize: parseInt(this.chartMax / 10),
              },
            },
          },
        },
      });

      myChart;
    },
  },
  mounted() {
    this.end = this.pet.weightList.length;
    this.start = Math.max(0, this.end - 10);
    for (var j = 0; j < this.pet.weightList.length; j++) {
      this.chartMax = Math.max(this.chartMax, this.pet.weightList[j].weight);
    }
    this.chartMax = (parseInt(this.chartMax / 5) + 1) * 5;

    const weights = [];
    const dates = [];
    for (var i = this.start; i < this.end; i++) {
      weights.push(this.pet.weightList[i].weight);
      let date = this.pet.weightList[i].date.substring(2, 4) + "." + this.pet.weightList[i].date.substring(4, 6) + "." + this.pet.weightList[i].date.substring(6, 8);
      dates.push(date);
    }

    const ctx = document.getElementById("myChart");

    myChart = new Chart(ctx, {
      type: "line",
      data: {
        labels: dates,
        // labels: this.chartjsLabel,
        datasets: [
          {
            label: this.pet.name + " 몸무게",
            pointBackgroundColor: "orange",
            data: weights,
            // data: this.chartjsData,
            backgroundColor: ["rgba(255, 165, 0)"],
            borderColor: ["rgba(255, 165, 0)"],
            borderWidth: 5, //선 굵기
          },
        ],
      },
      options: {
        plugins: {
          legend: {
            display: false, //라벨
            position: "top",
            labels: {
              boxWidth: 0, //동그라미
              padding: 0,
              usePointStyle: true,
              pointStyle: "circle",
              font: {
                size: 10,
              },
            },
            fullSize: true,
            align: "center",
          },
        },
        responsive: false,
        scales: {
          x: {
            stacked: false,
            display: true,
          },
          y: {
            beginAtZero: true,
            min: 0,
            max: this.chartMax,
            ticks: {
              stepSize: parseInt(this.chartMax / 10),
            },
          },
        },
      },
    });

    myChart;
  },
};
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->

<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}

.set {
  width: 100%;
}

.text-left{
  text-align: left;
}
</style>