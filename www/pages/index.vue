<template>
  <v-container fluid style="height: 100%;" class="ma-0 py-0">
    <v-row justify="center" align="center" style="height: 100%;">
      <v-col cols="12" xl="8" class="ma-0 py-0">
        <v-row align="center" justify="center">
          <v-col
            v-for="card in cards"
            :key="card.title"
            cols="12"
            md="7"
            class="my-2"
          >
            <welcome-card
              :title="card.title"
              :subtitle="card.subtitle"
              :btn="card.btn"
              :link="card.link"
            />
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  components: {
    WelcomeCard: () => import('~/components/WelcomeCard.vue')
  },
  data () {
    return {
      title: 'トップページ',
      cards: [
        {
          title: '投票を作る',
          subtitle: '新たに投票を作成します。',
          btn: '新規作成',
          link: '/create'
        },
        {
          title: '投票する',
          subtitle: '開催中の設問に投票します。',
          btn: '設問一覧',
          link: '/voting'
        },
        {
          title: '招待する',
          subtitle: '新しく投票に参加できるユーザーを招待します。',
          btn: '招待',
          link: '/invite'
        }
      ]
    }
  },
  beforeCreate () {
    const token = this.$store.state.Token.aToken
    if (token === '') {
      this.$router.push('/login')
    }
  },
  head ():any {
    return {
      title: this.title
    }
  }
})
</script>
