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
      <v-menu offset-y :close-on-content-click="false">
        <template v-slot:activator="{attr,on}">
          <v-avatar size="40" color="primary" v-bind="attr" v-on="on">
            {{ avatarText }}
          </v-avatar>
        </template>
        <v-list>
          <v-list-item @click="$router.push('/profile')">
            <v-list-item-avatar>
              <v-avatar size="40" color="primary" style="color: white;">
                <span>{{ avatarText }}</span>
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
    </v-app-bar>
    <v-main>
      <nuxt />
    </v-main>
  </v-app>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
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
    avatarText () {
      return this.$store.state.LoginUser.name.substr(0, 2)
    },
    user () {
      return this.$store.state.LoginUser
    },
    isLogin () {
      return this.$store.state.LoginUser.id !== 0
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
