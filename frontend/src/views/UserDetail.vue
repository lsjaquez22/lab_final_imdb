<template>
  <div class="container-movie">
    <p>User: {{user_name}}</p>
  </div>
</template>

<script>
export default {
  name: "Movie",
  data() {
    return {
      user_name: this.$route.params.user_name
    };
  },
  mounted() {
    console.log("User Page");
  }
};
</script>

<style lang="scss">
@import "../styles/_variables.scss";

.container-movie {
  height: 100vh;
  background-color: $black;
  p {
    color: $white;
  }
}
</style>