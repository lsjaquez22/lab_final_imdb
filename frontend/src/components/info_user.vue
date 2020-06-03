<template>
  <div>
    <div class="columns is-gapless">
      <div class="column">
        <div class="field">
          <label class="label">Username</label>
          <div class="control">
            <input
              class="input"
              type="text"
              placeholder="Normal input"
              v-model="user_info.username"
              :disabled="!edit_mode"
            />
          </div>
        </div>
      </div>
      <div class="column">
        <div class="field">
          <label class="label">Email</label>
          <div class="control">
            <input
              class="input"
              type="text"
              placeholder="Normal input"
              v-model="user_info.email"
              :disabled="!edit_mode"
            />
          </div>
        </div>
      </div>
      <div class="column">
        <div class="field">
          <label class="label">Name</label>
          <div class="control">
            <input
              class="input"
              type="text"
              placeholder="Normal input"
              v-model="user_info.name"
              :disabled="!edit_mode"
            />
          </div>
        </div>
      </div>
    </div>
    <div class="button-edit-info">
      <button v-if="!edit_mode" class="button is-primary" v-on:click="change_mode(true)">
        <strong>Edit</strong>
      </button>
      <button v-else class="button is-primary" v-on:click="change_mode(false)">
        <strong>Save</strong>
      </button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      edit_mode: false
    };
  },
  computed: {
    user_info() {
      return this.$store.state.user.user;
    }
  },
  methods: {
    change_mode(state) {
      this.edit_mode = state;
    }
  }
};
</script>

<style lang="scss" scoped>
@import "../styles/_variables.scss";

#app {
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
      font-size: 20px;
      color: $white;
      text-align: initial;
    }
  }
}
</style>