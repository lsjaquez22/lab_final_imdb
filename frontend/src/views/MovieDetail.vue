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
            <p class="card-footer-item">Score: {{movie.score}}</p>
            <p class="card-footer-item">Year: {{movie.year}}</p>
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
      movie: {}
    };
  },
  async mounted() {
    const res = await axios.get(
      `http://localhost:8080/api/movie/${this.movie_id}`,
      { headers: { Token: this.$store.state.user.token } }
    );
    this.movie = res.data;
    console.log(this.movie);
    return res.data;
  },
  components: {
    RecommendedFriends
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