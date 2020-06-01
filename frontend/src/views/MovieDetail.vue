<template>
  <div class="container-movie">
    <p>Movie: {{movie_name}}</p>
  </div>
</template>

<script>
export default {
  name: "MovieDetail",
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
  min-height: 100vh;
  background-color: $black;
  p {
    color: $white;
  }
}
</style>