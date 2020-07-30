<template>
  <v-container fluid style="height: 100%;" class="ma-0 py-0">
    <v-row justify="center" align="center" style="height: 100%;">
      <v-col cols="12" class="ma-0 py-0">
        <v-row align="center" justify="center">
          <v-col v-for="card in cards" :key="card.title" div cols="12" class="my-2">
            <welcome-card
              :title="card.title"
              :subtitle="card.subtitle"
              :btn="card.btn"
            />
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  components: {
    WelcomeCard: () => import('~/components/WelcomeCard')
  },
  data () {
    return {
      title: 'トップページ',
      cards: [
        {
          title: '投票を作る',
          subtitle: '新たに投票を作成します。',
          btn: '新規作成'
        },
        {
          title: '投票する',
          subtitle: '開催中の設問に投票します。',
          btn: '設問一覧'
        },
        {
          title: '招待する',
          subtitle: '新しく投票に参加できるユーザーを招待します。',
          btn: '招待'
        }
      ]
    }
  },
  head () {
    return {
      title: this.title
    }
  }
}
</script>
