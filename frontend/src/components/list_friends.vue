<template>
  <div class="column is-3 recomended-friends">
    <p class="subtitle is-4">Friends</p>
    <CardRecommendedUser
      v-for="user in list_friends"
      :key="user.username"
      :user="user"
      :profile="true"
    />
  </div>
</template>

<script>
import CardRecommendedUser from "./card_recommended_user";
export default {
  name: "ListFriends",
  props: {},
  components: {
    CardRecommendedUser
  },
  mounted() {
    this.$store.dispatch("get_friends_user");
  },
  computed: {
    list_friends() {
      return this.$store.state.friends_user;
    }
  }
};
</script>

<style lang="scss">
@import "../styles/_variables.scss";

.recomended-friends {
  margin: 0 1em;
  .subtitle {
    color: $white;
    font-weight: bold;
  }
}
</style>