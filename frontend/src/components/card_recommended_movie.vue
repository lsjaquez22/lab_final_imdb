<template>
  <div>
    <div class="container-card">
      <div class="card">
        <div class="card-image">
          <figure class="image is-4by3">
            <img :src="movie.poster" alt="Placeholder image" />
          </figure>
        </div>
        <div class="card-content">
          <div class="media">
            <div class="media-content">
              <router-link :to="{name:'Movie', params: {movie_id : movie.imdbID}}">
                <p class="title is-4">{{movie.title}}</p>
              </router-link>
              <p class="subtitle is-6">{{movie.year}}</p>
            </div>
          </div>
        </div>
        <footer class="card-footer">
          <p class="card-footer-item subtitle" v-on:click="addMovie(movie.imdbID)">
            Add Movie
            <i class="fas fa-film"></i>
          </p>
        </footer>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "CardRecommendedMovie",
  props: {
    movie: Object
  },
  methods: {
    addMovie(imdbID) {
      axios({
        method: "get",
        url: `http://localhost:8080/api/movie/${imdbID}`,
        headers: {
          Token: this.$store.state.user.token
        }
      }).then(() => {
        axios({
          method: "post",
          url: `http://localhost:8080/api/watchlist`,
          headers: {
            Token: this.$store.state.user.token
          },
          data: {
            imdbID: imdbID,
            state: "PLAN_TO_WATCH"
          }
        }).then(() => {
          this.$store.dispatch("get_user_movies");
          this.$store.dispatch("get_recommended_movies");
        });
      });
    }
  }
};
</script>

<style lang="scss" scoped>
@import "../styles/_variables.scss";
.container-card {
  width: 260px;
  margin: 0 2em 2em 2em;
  a {
    .title {
      margin-bottom: 10px;
    }
  }
  .card-content {
    min-height: 160px;
  }
  .card-footer {
    background-color: $purple;
    p {
      color: $white;
      i {
        margin-left: 10px;
      }
    }
  }
  .card-footer:hover {
    cursor: pointer;
  }
}
</style>