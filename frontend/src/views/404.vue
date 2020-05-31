<template>
  <div class="container-404">
    <h1>Page Not Found</h1>
    <router-link to="/">
      <button class="button is-primary">
        <strong>Home</strong>
      </button>
    </router-link>
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

.container-404 {
  height: 100vh;
  background-color: $black;
  p {
    color: $white;
  }
  .is-primary {
    background-color: $purple;
    strong {
      color: $white;
    }
  }
}
</style>