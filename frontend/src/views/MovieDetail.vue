<template>
  <div class="container-movie">
    <p>Movie: {{movie_name}}</p>
  </div>
</template>

<script>
export default {
  name: "Movie",
  data() {
    return {
      movie_name: this.$route.params.movie_name
    };
  },
  mounted() {
    console.log("Movie Page");
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