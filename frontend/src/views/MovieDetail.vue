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
              <button v-if="!in_watch_list" @click="addMovie()" class="card-footer-item subtitle">
                Add Movie
                <i class="fas fa-film"></i>
              </button>
              <button
                v-else
                @click="deleteMovie()"
                class="card-footer-item subtitle delete-movie"
              >Delete Movie</button>
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
                      <i class="fas fa-star star" aria-hidden="true"></i>
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
          <div class="card-content">
            <div class="card" v-for="element in comments" :key="element.id">
              <div class="card-header-title">
                <p>{{element.user.username}}</p>
              </div>

              <div v-show="!editFlag" class="card-content">
                <p>{{element.comment}}</p>
                <small>{{element.date}}</small>
              </div>
              <textarea
                v-show="editFlag && currentCommentId === element.id"
                v-model="comment"
                maxlength="255"
                class="textarea"
                :placeholder="element.comment"
              ></textarea>
              <div class="card-footer" v-show="username == element.user.username">
                <div class="card-footer-item" />
                <div class="columns is-mobile">
                  <div class="column">
                    <div>
                      <i class="fas fa-times" @click="deleteComment(element.id)"></i>
                    </div>
                  </div>
                  <div class="column">
                    <div v-show="!editFlag">
                      <i class="fas fa-edit" @click="toggleFlag(element.id, element.comment)"></i>
                    </div>
                    <div v-show="editFlag">
                      <i class="fas fa-check" @click="updateComment(element.id)"></i>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="control card-footer-item">
            <textarea
              v-model="comment"
              maxlength="255"
              class="textarea"
              placeholder="Write a comment..."
            ></textarea>
          </div>
          <footer class="card-footer">
            <button @click="makeComment()" class="card-footer-item subtitle">Add Comment</button>
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
        <RecommendedFriends class="customFriends" />
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
      userScore: 0,
      comment: "",
      updatedComment: "",
      editFlag: false,
      currentCommentId: 0,
      in_watch_list: false
    };
  },
  async mounted() {
    this.fetchMovie();
    this.getUsrScore();
    this.getComments();
  },
  computed: {
    movie() {
      return this.$store.state.tempMovie;
    },
    comments() {
      return this.$store.state.movieComments;
    },
    username() {
      return this.$store.state.user.user.username;
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
      this.in_watch_list = res.data.inWatchList;
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
        this.rateMovie(score);
      } else {
        this.changeRating(score);
      }
    },
    async getUsrScore() {
      const res = await axios.get(
        `http://localhost:8080/api/movie/${this.movie_id}/score`,
        { headers: { Token: this.$store.state.user.token } }
      );

      console.log("RESPONSE", res);
      this.userScore = res.data;
    },
    getComments() {
      this.$store.dispatch("fetchMovieComment", this.movie_id);
    },
    async makeComment() {
      if (this.comment != "") {
        const data = {
          date: new Date().toISOString().substr(0, 10),
          comment: this.comment
        };
        const res = await axios.post(
          `http://localhost:8080/api/movie/${this.movie_id}/comment`,
          data,
          { headers: { Token: this.$store.state.user.token } }
        );
        this.comment = "";
        this.getComments();
        return res;
      }
    },
    async deleteComment(id) {
      const res = await axios.delete(
        `http://localhost:8080/api/movie/comment/${id}`,
        { headers: { Token: this.$store.state.user.token } }
      );
      this.getComments();
      return res;
    },
    async updateComment(id) {
      const data = {
        comment: this.comment
      };
      const res = await axios.put(
        `http://localhost:8080/api/movie/comment/${id}`,
        data,
        { headers: { Token: this.$store.state.user.token } }
      );
      this.editFlag = false;
      this.getComments();
      this.comment = "";
      return res;
    },
    toggleFlag(id, comment) {
      this.currentCommentId = id;
      this.editFlag = true;
      this.comment = comment;
    },
    addMovie() {
      axios({
        method: "get",
        url: `http://localhost:8080/api/movie/${this.movie_id}`,
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
            imdbID: this.movie_id,
            state: "PLAN_TO_WATCH"
          }
        }).then(() => {
          this.$store.dispatch("get_user_movies");
          this.$store.dispatch("get_recommended_movies");
          this.in_watch_list = true;
        });
      });
    },
    deleteMovie() {
      axios({
        method: "delete",
        url: `http://localhost:8080/api/watchlist/${this.movie_id}`,
        headers: {
          Token: this.$store.state.user.token
        }
      }).then(() => {
        this.$store.dispatch("get_user_movies");
        this.$store.dispatch("get_recommended_movies");
        this.in_watch_list = false;
      });
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

  .dropdown-content {
    background-color: $black;
  }

  .customFriends {
    background-color: $black;
    -webkit-box-shadow: 0px 0px 22px 0px rgba(0, 0, 0, 0.75);
    -moz-box-shadow: 0px 0px 22px 0px rgba(0, 0, 0, 0.75);
    box-shadow: 0px 0px 22px 0px rgba(0, 0, 0, 0.75);
  }
  a {
    color: $white;
  }

  button {
    background-color: $purple;
    color: $white;
  }
  .delete-movie {
    background-color: $red;
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
      border: none;

      small {
        color: $purple;
      }
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
    i {
      color: $purple;
    }

    .star {
      color: $white;
    }
  }
}
</style>