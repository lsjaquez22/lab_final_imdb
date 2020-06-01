<template>
  <div class="container-profile">
    <div class="home">
      <div class="columns is-gapless">
        <div class="column">
          <h1>{{user_info.username}}</h1>
          <h1>{{user_info.email}}</h1>
          <h1>{{user_info.name}}</h1>
        </div>
        <RecommendedFriends />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Profile",
  components: {},
  computed: {
    user_info() {
      return this.$store.state.user.user;
    }
  }
};
</script>

<style lang="scss">
@import "../styles/_variables.scss";

.container-profile {
  min-height: 100vh;
  background-color: $black;
  h1 {
    color: $white;
  }
}
</style>

