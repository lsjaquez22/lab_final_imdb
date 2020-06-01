<template>
  <article class="message">
    <div class="message-body">
      <div class="user-info">
        <router-link :to="{name:'User', params: {user_name: user.username}}">
          <strong>@{{user.username}}</strong>
          <p>{{user.name}}</p>
        </router-link>
      </div>

      <div class="icon-add-friend">
        <i class="fas fa-user-plus"></i>
      </div>
    </div>
  </article>
</template>

<script>
export default {
  name: "CardUser",
  props: {
    user: Object
  }
};
</script>

<style lang="scss" scoped>
@import "../styles/_variables.scss";
#app {
  .message-body {
    border-color: $purple;
    border-width: 0 0 0 6px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    .user-info {
      text-align: initial;
      a {
        text-decoration: none;
      }
    }
    i:hover {
      cursor: pointer;
    }
  }
}
</style>