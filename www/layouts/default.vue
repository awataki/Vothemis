<template>
  <v-app>
    <v-navigation-drawer
      v-if="isLogin"
      v-model="drawer"
      clipped
      app
      width="220px"
      overlay-color="primary"
    >
      <v-list class="py-0">
        <v-list-item
          v-for="(item, i) in items"
          :key="i"
          :to="item.to"
          router
          exact
        >
          <v-list-item-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title v-text="item.title" />
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <v-app-bar
      :clipped-left="true"
      fixed
      flat
      app
      dark
      color="black"
    >
      <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
      <v-toolbar-title />
      <v-spacer />
      <UserAvatar v-if="isLogin" :user="user" />
    </v-app-bar>
    <v-main>
      <nuxt />
    </v-main>
  </v-app>
</template>

<script lang="ts">
import Vue from 'vue'
import UserAvatar from '~/components/UserAvatar.vue'

export default Vue.extend({
  components: {
    UserAvatar
  },
  data () {
    return {
      drawer: undefined,
      items: [
        {
          icon: 'mdi-home',
          title: 'ようこそ',
          to: '/'
        },
        {
          icon: 'mdi-pencil',
          title: '投票をつくる',
          to: '/create'
        },
        {
          icon: 'mdi-vote',
          title: '投票する',
          to: '/voting'
        },
        {
          icon: 'mdi-account',
          title: 'プロフィール',
          to: '/profile'
        },
        {
          icon: 'mdi-account-multiple-plus',
          title: 'ユーザー招待',
          to: '/invite'
        }
      ],
      miniVariant: false,
      right: true,
      rightDrawer: false
    }
  },
  computed: {
    user () {
      return this.$store.state.LoginUser
    },
    isLogin () {
      return this.$store.state.LoginUser.id !== 0
    }
  }
})
</script>
