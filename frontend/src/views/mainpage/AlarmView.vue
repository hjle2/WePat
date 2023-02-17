<template>
  <div v-show="notification.length === 0" class="no-image">알람이 없습니다.</div>
  <AlarmItem :notification="this.notification"/>
</template>

<script>
import AlarmItem from "@/components/mainpage/AlarmItem.vue"

export default {
  name: "AlarmView",
  components: { AlarmItem },
  data() {
    return {
      // notificationId: null,
      notification: [],
    }
  },
  created() {
    this.getNotification()
    console.log(this.notification)
  },
  mounted(){
    // window.addEventListener('beforeunload', this.unLoadEvent)
    this.getNotification()
  },

  methods: {
    getNotification() {
      const dataSet = this.$store.dispatch("getNotification")
      dataSet.then((getData) => {
        this.notification = getData
      })
    },
  },
}
</script>

<style></style>
