<template>
  <div class="container-movies-list">
    <div class="columns is-gapless">
      <div class="column is-1"></div>
      <div class="column">
        <h1 class="title">List of Movies</h1>
        <div class="movies">
          <CardMovie v-for="element in list_movies" :key="element.imdbID" :movie="element" />
        </div>
      </div>
      <div class="column is-1"></div>
    </div>
  </div>
</template>

<script>
import CardMovie from "../components/card_movie.vue";
export default {
  name: "Movie",
  components: {
    CardMovie
  },
  data() {
    return {};
  },
  computed: {
    list_movies() {
      return this.$store.state.suggested_movies;
    }
  }
};
</script>

<style lang="scss">
@import "../styles/_variables.scss";

.container-movies-list {
  min-height: 100vh;
  background-color: $black;
  h1 {
    color: $white;
    margin-top: 2em;
    margin-bottom: 1em;
  }
  .movies {
    margin-top: 2em;
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
  }
}
</style>