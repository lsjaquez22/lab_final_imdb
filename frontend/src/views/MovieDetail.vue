<template>
  <div class="container-movie">
    <div class="columns">
      <div class="column is-3">
        <div class="card">
          <p class="title">{{movie.title}}</p>
          <div class="card-image">
            <figure class="image is-2by3">
              <img :src="movie.poster" alt="Placeholder image" />
            </figure>
          </div>
          <div class="card-content">
            <footer class="card-footer">
              <button class="card-footer-item subtitle">
                Add Movie
                <i class="fas fa-film"></i>
              </button>
            </footer>
          </div>
        </div>

        <div class="card">
          <div class="title">More Info</div>
          <div class="card-content">
            <p>
              <strong>Release date:</strong>
              {{movie.released}}
            </p>
            <p>
              <strong>Genre:</strong>
              {{movie.genre}}
            </p>
            <p>
              <strong>Writer:</strong>
              {{movie.writer}}
            </p>
            <p>
              <strong>Awards:</strong>
              {{movie.awards}}
            </p>
            <p>
              <strong>Country:</strong>
              {{movie.country}}
            </p>
            <p>
              <strong>Language:</strong>
              {{movie.language}}
            </p>
          </div>
        </div>
      </div>
      <div class="column is-6">
        <div class="card">
          <footer class="card-footer">
            <p class="card-footer-item">
              Score: {{movie.score}}
              <progress
                class="progress is-primary is-small"
                :value="movie.score"
                max="5"
              ></progress>
            </p>
            <p class="card-footer-item">Year: {{movie.year}}</p>
            <div class="card-footer-item">
              <div class="dropdown is-hoverable">
                <div class="dropdown-trigger">
                  <p>Rate:</p>
                  <button class="button" aria-haspopup="true" aria-controls="dropdown-menu4">
                    <span>
                      {{userScore}}
                      <i class="fas fa-star" aria-hidden="true"></i>
                    </span>
                    <span class="icon is-small">
                      <i class="fas fa-angle-down" aria-hidden="true"></i>
                    </span>
                  </button>
                </div>
                <div class="dropdown-menu" id="dropdown-menu4" role="menu">
                  <div class="dropdown-content">
                    <a @click="handleRating(1.0)" class="dropdown-item">
                      (1)
                      <i class="fas fa-star" aria-hidden="true"></i>
                    </a>
                    <hr class="dropdown-divider" />
                    <a @click="handleRating(2.0)" class="dropdown-item">
                      (2)
                      <i class="fas fa-star" aria-hidden="true"></i>
                      <i class="fas fa-star" aria-hidden="true"></i>
                    </a>
                    <hr class="dropdown-divider" />
                    <a @click="handleRating(3.0)" class="dropdown-item">
                      (3)
                      <i class="fas fa-star" aria-hidden="true"></i>
                      <i class="fas fa-star" aria-hidden="true"></i>
                      <i class="fas fa-star" aria-hidden="true"></i>
                    </a>
                    <hr class="dropdown-divider" />
                    <a @click="handleRating(4.0)" class="dropdown-item">
                      (4)
                      <i class="fas fa-star" aria-hidden="true"></i>
                      <i class="fas fa-star" aria-hidden="true"></i>
                      <i class="fas fa-star" aria-hidden="true"></i>
                      <i class="fas fa-star" aria-hidden="true"></i>
                    </a>
                    <hr class="dropdown-divider" />
                    <a @click="handleRating(5.0)" class="dropdown-item">
                      (5)
                      <i class="fas fa-star" aria-hidden="true"></i>
                      <i class="fas fa-star" aria-hidden="true"></i>
                      <i class="fas fa-star" aria-hidden="true"></i>
                      <i class="fas fa-star" aria-hidden="true"></i>
                      <i class="fas fa-star" aria-hidden="true"></i>
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </footer>
        </div>
        <div class="card">
          <div class="card-header-title">Description:</div>
          <div class="card-content">
            <p>{{movie.plot}}</p>
          </div>
        </div>
        <div class="card">
          <div class="card-header-title">Comments:</div>
          <div class="card-content">COMMENTS HERE</div>
          <div class="control card-footer-item">
            <textarea maxlength="255" class="textarea" placeholder="Write a comment..."></textarea>
          </div>
          <footer class="card-footer">
            <button class="card-footer-item subtitle">Add Comment</button>
          </footer>
        </div>
      </div>
      <div class="column is-3">
        <div class="card actors-card">
          <div class="card-header-title">Actors</div>
          <div class="card-content">
            <p>{{movie.actors}}</p>
          </div>
        </div>
        <RecommendedFriends />
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import RecommendedFriends from "../components/recommended_friends_mdetail";

export default {
  name: "MovieDetail",
  data() {
    return {
      movie_id: this.$route.params.movie_id,
      score: 0,
      userScore: 0
    };
  },
  async mounted() {
    this.fetchMovie();
    this.getUsrScore();
  },
  computed: {
    movie() {
      return this.$store.state.tempMovie;
    }
  },
  components: {
    RecommendedFriends
  },
  methods: {
    async fetchMovie() {
      const res = await axios.get(
        `http://localhost:8080/api/movie/${this.movie_id}`,
        { headers: { Token: this.$store.state.user.token } }
      );
      this.$store.dispatch("fetchTempMovie", res.data.movie);
    },
    async rateMovie(score) {
      const data = {
        imdbID: this.movie_id,
        score: score
      };
      const res = await axios.post(
        "http://localhost:8080/api/movie/score",
        data,
        { headers: { Token: this.$store.state.user.token } }
      );

      if (res.status === 200) {
        console.log(res);
        this.userScore = score;
        this.fetchMovie();
      }
    },
    async changeRating(score) {
      const data = {
        imdbID: this.movie_id,
        score: score
      };
      const res = await axios.put(
        "http://localhost:8080/api/movie/score",
        data,
        { headers: { Token: this.$store.state.user.token } }
      );

      if (res.status === 200) {
        console.log(res);
        this.userScore = score;
        this.fetchMovie();
      }
    },
    handleRating(score) {
      if (this.userScore === 0.0) {
        this.changeRating(score);
        // this.rateMovie(score);
      } else {
        this.changeRating(score);
      }
    },
    async getUsrScore() {
      const res = await axios.get(
        `http://localhost:8080/api/movie/·${this.movie_id}/score`,
        { headers: { Token: this.$store.state.user.token } }
      );

      console.log(res.data);
      this.userScore = res.data;
    }
  }
};
</script>

<style lang="scss" scoped>
@import "../styles/_variables.scss";

.container-movie {
  min-height: 100vh;
  background-color: $purple;
  p {
    margin: 3%;
    color: $white;
  }

  button {
    background-color: $purple;
    color: $white;
  }

  .actors-card {
    margin: 50px;
  }
  .card {
    margin: 5%;
    background-color: $black;
    -webkit-box-shadow: 0px 0px 22px 0px rgba(0, 0, 0, 0.75);
    -moz-box-shadow: 0px 0px 22px 0px rgba(0, 0, 0, 0.75);
    box-shadow: 0px 0px 22px 0px rgba(0, 0, 0, 0.75);
    .title {
      color: $white;
    }
    .card-header-title {
      color: $white;
    }
    .card-footer {
      background-color: $black;
    }
    .card-footer-item {
      border: none;
    }

    .card-content {
      text-align: left;

      strong {
        color: $white;
      }
    }
  }
  .card-footer {
    background-color: $purple;
    p {
      color: $white;
    }
  }
}
</style>