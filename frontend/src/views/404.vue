<template>
  <div class="container-404">
    <h1 class="title">Page Not Found</h1>
    <router-link to="/">
      <button class="button is-primary">
        <strong>Home</strong>
      </button>
    </router-link>
  </div>
</template>

<script>
export default {
  name: "404",
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
  min-height: 100vh;
  background-color: $black;
  padding-top: 10em;
  h1 {
    color: $white;
  }
  .is-primary {
    background-color: $purple;
    strong {
      color: $white;
    }
  }
  .is-primary:hover {
    background-color: $purple;
  }
}
</style>