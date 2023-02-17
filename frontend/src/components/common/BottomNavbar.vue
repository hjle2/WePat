<template>
  <footer class="">
      <div class="row bottom-navbar">
        <!-- <img class="line_logo" alt="select_bar1" src="@/assets/select_bar1.png"> -->
        <router-link to="/mainpage" class="grid col-2 col-xl-1">
          <img class="logo not-bordered-logo" alt="icon_calendar_check" src="@/assets/icon_calendar.png">
        </router-link>
        <router-link to="/financial" class="grid col-2 col-xl-1">
          <img class="logo not-bordered-logo" alt="icon_financial" src="@/assets/icon_financial.png">
        </router-link>
        <router-link to="/sns" class="grid col-2 col-xl-1">
          <img class="logo not-bordered-logo" alt="icon_people_audience" src="@/assets/icon_people_audience.png">
        </router-link>
        <router-link to="/album" class="grid col-2 col-xl-1">
          <img class="logo not-bordered-logo" alt="icon_photo" src="@/assets/icon_photo.png">
        </router-link>
        <router-link to="/user" class="grid col-2 col-xl-1">
          <img class="logo-user not-bordered-logo" alt="icon_profile" src="@/assets/icon_profile.png">
        </router-link>
      </div>
</footer>
  
</template>

<script>
export default {
    name : 'BottomNavbar',
  }
</script>

<style>
</style>
