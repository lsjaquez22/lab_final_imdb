<template>
  <div class="container-profile">
    <h1>This is a profile page</h1>
  </div>
</template>

<script>
export default {
  name: "Profile",
  components: {}
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

