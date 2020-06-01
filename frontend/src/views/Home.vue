<template>
  <div class="home">
    <div class="columns is-gapless">
      <div class="column recomended-movies">
        <p class="subtitle is-3">Recommended Movies For You</p>
        <div class="movies-container">
          <CardMovie v-for="index in numberToShow" :key="index" :movie="list_movies[index-1]" />
        </div>
        <div v-if="!showAll" class="show-more">
          <button class="button is-primary" v-on:click="show_more()">
            <strong>Show More...</strong>
          </button>
        </div>
      </div>
      <div class="column is-3 recomended-friends">
        <p class="subtitle is-4">People You May Know</p>
      </div>
    </div>
  </div>
</template>

<script>
import CardMovie from "../components/card_movie.vue";
export default {
  name: "Home",
  components: { CardMovie },
  data() {
    return {
      numberToShow: 3,
      showAll: false
    };
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

.home {
  min-height: 100vh;
  background-color: $black;
  color: $white;
  padding-top: 3em;
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
        background-color: $purple;
        strong {
          color: $white;
        }
      }
    }
  }
  .recomended-friends {
    margin: 0 1em;
  }
}
</style>
