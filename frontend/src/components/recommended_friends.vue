<template>
  <div class="column is-3 recomended-friends">
    <p class="subtitle is-4">People You May Know</p>
    <CardRecommendedUser
      v-for="user in list_users"
      :key="user.username"
      :user="user"
      :profile="false"
    />
  </div>
</template>

<script>
import CardRecommendedUser from "./card_recommended_user";
export default {
  name: "RecommendedFriends",
  props: {},
  components: {
    CardRecommendedUser
  },
  mounted() {
    this.$store.dispatch("get_recommended_users");
  },
  computed: {
    list_users() {
      return this.$store.state.recommended_users;
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