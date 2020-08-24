<template>
  <v-menu offset-y :close-on-content-click="false">
    <template v-slot:activator="{attr,on}">
      <v-avatar size="40" color="primary" v-bind="attr" v-on="on">
        {{ user.name.substr(0,2) }}
      </v-avatar>
    </template>
    <v-list>
      <v-list-item @click="$router.push('/profile')">
        <v-list-item-avatar>
          <v-avatar size="40" color="primary" style="color: white;">
            <span>{{ user.name.substr(0,2) }}</span>
          </v-avatar>
        </v-list-item-avatar>
        <v-list-item-content>
          <v-list-item-title>
            {{ user.name }}
          </v-list-item-title>
          <v-list-item-subtitle>
            {{ user.bio }}
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
      <v-list-item>
        <v-list-item-title>
          ダークテーマ
        </v-list-item-title>
        <v-list-item-action>
          <v-switch
            v-model="$vuetify.theme.dark"
            hide-details
            inset
          />
        </v-list-item-action>
      </v-list-item>
      <v-list-item @click="logout">
        <v-list-item-title color="warning">
          <span style="color: #ff5252;">
            ログアウト
          </span>
        </v-list-item-title>
      </v-list-item>
    </v-list>
  </v-menu>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  props: {
    user: {
      type: Object,
      required: true
    }
  },
  methods: {
    logout () {
      this.$store.commit('LoginUser/logout')
      this.$store.commit('Token/removeAccessToken')
      this.$router.push('/login')
    }
  }
})
</script>
