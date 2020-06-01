<template>
  <div class="container-movie">
    <div class="home">
      <div class="columns is-gapless">
        <div class="column">
          <div class="columns is-gapless is-vcentered">
            <div class="column">
              <p class="title is-2">
                <b>@{{user_name}}</b>
              </p>
              <p class="title is-3">{{user_info.name}}</p>
            </div>
            <div class="column" v-if="!my_friends">
              <button class="button is-primary">
                <strong>Add Friend</strong>
              </button>
            </div>
          </div>
          <p class="title is-3">Movies</p>
          <div class="movies-container">
            <CardUserMovie v-for="element in user_movies" :key="element.imdbID" :movie="element" />
          </div>
        </div>
        <RecommendedFriends />
      </div>
    </div>
  </div>
</template>

<script>
import CardUserMovie from "../components/card_user_movie";
import RecommendedFriends from "../components/recommended_friends.vue";
export default {
  name: "User",
  components: {
    CardUserMovie,
    RecommendedFriends
  },
  data() {
    return {
      user_name: this.$route.params.user_name,
      my_friends: false,
      user_info: {
        username: "HMH",
        name: "Humberto Morales"
      },
      user_movies: [
        {
          Title: "The Wolf of Wall Street",
          Year: "2013",
          imdbID: "tt0993846",
          Type: "movie",
          Poster:
            "https://m.media-amazon.com/images/M/MV5BMjIxMjgxNTk0MF5BMl5BanBnXkFtZTgwNjIyOTg2MDE@._V1_SX300.jpg",
          state: "WATCHING"
        },
        {
          Title: "21 Jump Street",
          Year: "2012",
          imdbID: "tt1232829",
          Type: "movie",
          Poster:
            "https://m.media-amazon.com/images/M/MV5BNTZjNzRjMTMtZDMzNy00Y2ZjLTg0OTAtZjVhNzYyZmJjOTljXkEyXkFqcGdeQXVyODE5NzE3OTE@._V1_SX300.jpg",
          state: "WATCHING"
        },
        {
          Title: "22 Jump Street",
          Year: "2014",
          imdbID: "tt2294449",
          Type: "movie",
          Poster:
            "https://m.media-amazon.com/images/M/MV5BMTcwNzAxMDU1M15BMl5BanBnXkFtZTgwNDE2NTU1MTE@._V1_SX300.jpg",
          state: "WATCHING"
        },
        {
          Title: "The Hangover",
          Year: "2009",
          imdbID: "tt1119646",
          Type: "movie",
          Poster:
            "https://m.media-amazon.com/images/M/MV5BNGQwZjg5YmYtY2VkNC00NzliLTljYTctNzI5NmU3MjE2ODQzXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg",
          state: "WATCHING"
        },
        {
          Title: "The Hangover Part II",
          Year: "2011",
          imdbID: "tt1411697",
          Type: "movie",
          Poster:
            "https://m.media-amazon.com/images/M/MV5BMTM2MTM4MzY2OV5BMl5BanBnXkFtZTcwNjQ3NzI4NA@@._V1_SX300.jpg",
          state: "WATCHING"
        },
        {
          Title: "The Hangover Part III",
          Year: "2013",
          imdbID: "tt1951261",
          Type: "movie",
          Poster:
            "https://m.media-amazon.com/images/M/MV5BMTA0NjE1MzMzODheQTJeQWpwZ15BbWU3MDY4MTQ3Mzk@._V1_SX300.jpg",
          state: "WATCHING"
        }
      ]
    };
  },
  mounted() {
    console.log("get info User (info and movies)");
    console.log("get info if user is my friend");
  }
};
</script>

<style lang="scss" scoped>
@import "../styles/_variables.scss";

#app {
  .home {
    min-height: 100vh;
    background-color: $black;
    color: $white;
    padding-top: 3em;
    .subtitle {
      color: $white;
      font-weight: bold;
    }
    .recomended-friends {
      margin: 0 1em;
    }
    .movies-container {
      margin-top: 2em;
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
    }
    .is-primary {
      background-color: $purple;
      strong {
        color: $white;
      }
    }
  }
}

.container-movie {
  min-height: 100vh;
  background-color: $black;
  p {
    color: $white;
  }
}
</style>