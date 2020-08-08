<template>
  <v-card shaped max-width="1024px" class="mx-auto">
    <v-card-title>
      {{ title }}
    </v-card-title>
    <v-card-subtitle>
      {{ subtitle }}
    </v-card-subtitle>
    <v-card-actions>
      <v-btn class="ma-2" :to="link" nuxt>
        {{ btn }}
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<style lang="scss" scoped>
.v-btn {
  color: white !important;
  background-color: #212121 !important;
  min-width: 95px !important;
}
</style>

<script lang="ts">
import Vue from 'vue'
export default Vue.extend({
  props: {
    title: {
      type: String,
      required: true
    },
    subtitle: {
      type: String,
      required: true
    },
    btn: {
      type: String,
      required: true
    },
    link: {
      type: String,
      required: true
    }
  }
})
</script>
