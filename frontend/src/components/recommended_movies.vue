<template>
  <div class="column recomended-movies">
    <p class="subtitle is-3">Recommended Movies For You</p>
    <div class="movies-container">
      <CardRecommendedMovie
        v-for="index in numberToShow"
        :key="index"
        :movie="list_movies[index-1]"
      />
    </div>
    <div v-if="!showAll" class="show-more">
      <button class="button is-primary" v-on:click="show_more()">
        <strong>Show More...</strong>
      </button>
    </div>
  </div>
</template>

<script>
import CardRecommendedMovie from "./card_recommended_movie";
export default {
  name: "RecommendedMovie",
  components: {
    CardRecommendedMovie
  },
  data() {
    return {
      showAll: false,
      numberToShow: 3
    };
  },
  mounted() {
    this.$store.dispatch("get_recommended_movies");
  },
  computed: {
    list_movies() {
      return this.$store.state.recommended_movies;
    }
  },
  methods: {
    show_more() {
      let total_movies = this.list_movies.length;
      if (this.numberToShow + 3 >= total_movies) {
        this.numberToShow = total_movies;
        this.showAll = true;
      } else {
        this.numberToShow += 3;
      }
    }
  }
};
</script>

<style lang="scss" scoped>
@import "../styles/_variables.scss";
.subtitle {
  color: $white;
  font-weight: bold;
}
.recomended-movies {
  .movies-container {
    margin-top: 2em;
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
  }
  .show-more {
    margin-top: 2em;
    margin-bottom: 4em;
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-end;
    .is-primary {
      margin-right: 3em;
      background-color: $purple;
      strong {
        color: $white;
      }
    }
  }
}
</style>