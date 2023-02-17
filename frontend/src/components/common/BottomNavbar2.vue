<template>
  <footer class="row">
    <div class="bottom-navbar">
      <!-- <img class="line_logo" alt="select_bar1" src="@/assets/select_bar1.png"> -->
      <router-link to="/mainpage" class="col-1">
        <img
          class="logo-main not-bordered-logo"
          alt="icon_calendar"
          src="@/assets/icon_calendar.png"
        />
      </router-link>
      <router-link to="/financial" class="col-1 xs-offset-1">
        <img
          class="logo-finance bordered-logo"
          alt="icon_financial_check"
          src="@/assets/icon_financial_check.png"
        />
      </router-link>
      <router-link to="/sns" class="col-1 xs-offset-1">
        <img
          class="logo-sns not-bordered-logo"
          alt="icon_people_audience"
          src="@/assets/icon_people_audience.png"
        />
      </router-link>
      <router-link to="/album" class="col-1 xs-offset-1">
        <img
          class="logo-album not-bordered-logo"
          alt="icon_photo"
          src="@/assets/icon_photo.png"
        />
      </router-link>
      <router-link to="/user" class="col-1 xs-offset-1">
        <img
          class="logo-user not-bordered-logo"
          alt="icon_profile"
          src="@/assets/icon_profile.png"
        />
      </router-link>
    </div>

  </footer>
</template>

<script>
export default {
  name: "BottomNavbar5",
}
</script>

<style>
</style>
