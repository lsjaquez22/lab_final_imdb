<template>
  <div>
    <div class="container-card">
      <div class="card">
        <div class="card-image">
          <figure class="image is-4by3">
            <img :src="movie.Poster" alt="Placeholder image" />
          </figure>
        </div>
        <div class="card-content">
          <div class="media">
            <div class="media-content">
              <router-link :to="{name:'Movie', params: {movie_name: movie.Title}}">
                <p class="title is-4">{{movie.Title}}</p>
              </router-link>
              <p class="subtitle is-6">{{movie.Year}}</p>
            </div>
          </div>

          <div class="content">
            <label class="label">State</label>
            <div class="select">
              <select v-model="movie.state" :disabled="true">
                <option value="WATCHING">WATCHING</option>
                <option value="COMPLETED">COMPLETED</option>
                <option value="PLAN_TO_WATCH">PLAN_TO_WATCH</option>
                <option value="ON_HOLD">ON_HOLD</option>
              </select>
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
  name: "CardUserMovie",
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
    min-height: 200px;
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