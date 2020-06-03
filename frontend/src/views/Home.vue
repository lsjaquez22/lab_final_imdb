<template>
  <div class="home">
    <div class="columns is-gapless">
      <RecommendedMovie />
      <RecommendedFriends />
    </div>
  </div>
</template>

<script>
import RecommendedMovie from "../components/recommended_movies.vue";
import RecommendedFriends from "../components/recommended_friends.vue";
export default {
  name: "Home",
  components: { RecommendedMovie, RecommendedFriends }
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
  .recomended-friends {
    margin: 0 1em;
  }
}
</style>
