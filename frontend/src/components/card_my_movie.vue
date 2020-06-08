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

          <div class="content">
            <label class="label">State</label>
            <div class="select">
              <select v-model="movie.state">
                <option value="WATCHING">WATCHING</option>
                <option value="COMPLETED">COMPLETED</option>
                <option value="PLAN_TO_WATCH">PLAN_TO_WATCH</option>
                <option value="ON_HOLD">ON_HOLD</option>
              </select>
            </div>
          </div>
        </div>
        <footer class="card-footer">
          <p class="card-footer-item subtitle edit-state" v-on:click="saveStateMovie(movie.imdbID)">
            Save State
            <i class="fas fa-film"></i>
          </p>
          <p class="card-footer-item subtitle delete-movie" v-on:click="deleteMovie(movie.imdbID)">
            Delete Movie
            <i class="fas fa-times"></i>
          </p>
        </footer>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "CardMyMovie",
  props: {
    movie: Object
  },
  methods: {
    saveStateMovie(imdbID) {
      axios({
        method: "put",
        url: `http://localhost:8080/api/watchlist`,
        headers: {
          Token: this.$store.state.user.token
        },
        data: {
          imdbID: imdbID,
          state: this.movie.state
        }
      }).then(() => {
        this.$store.dispatch("get_user_movies");
      });
    },
    deleteMovie(imdbID) {
      axios({
        method: "delete",
        url: `http://localhost:8080/api/watchlist/${imdbID}`,
        headers: {
          Token: this.$store.state.user.token
        }
      }).then(() => {
        this.$store.dispatch("get_user_movies");
        this.$store.dispatch("get_recommended_movies");
      });
    }
  }
};
</script>

<style lang="scss" scoped>
@import "../styles/_variables.scss";
#app {
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
      .edit-state {
        background-color: $purple;
      }
      .delete-movie {
        background-color: $red;
      }
      p {
        margin-bottom: 0;
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
}
</style>