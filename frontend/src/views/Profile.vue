<template>
  <div class="container-profile">
    <div class="home">
      <div class="columns is-gapless">
        <div class="column">
          <InfoUser />
          <p class="title">My Movies</p>
          <div class="movies-container">
            <CardMyMovie v-for="element in list_movies" :key="element.imdbID" :movie="element" />
          </div>
        </div>
        <ListFriends />
      </div>
    </div>
  </div>
</template>

<script>
import ListFriends from "../components/list_friends.vue";
import InfoUser from "../components/info_user.vue";
import CardMyMovie from "../components/card_my_movie.vue";
export default {
  name: "Profile",
  components: { ListFriends, InfoUser, CardMyMovie },
  computed: {
    list_movies() {
      return this.$store.state.movies_user;
    }
  }
};
</script>

<style lang="scss" scoped>
@import "../styles/_variables.scss";

#app {
  .container-profile {
    min-height: 100vh;
    background-color: $black;
    padding-top: 3em;
    h1 {
      color: $white;
    }
    .recomended-friends {
      margin: 0 1em;
    }
    .button-edit-info {
      text-align: end;
      padding-right: 3em;
      .is-primary {
        background-color: $purple;
        color: $white;
      }
    }
    .field {
      margin: 0 2em;
      .label {
        color: $white;
        text-align: initial;
      }
    }
    .title {
      color: white;
      margin-top: 2em;
    }
    .movies-container {
      margin-top: 2em;
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
    }
  }
}
</style>

