<template>
  <v-container>
    <v-row justify="center" align="start">
      <v-col cols="12" md="8">
        <v-data-table
          :headers="headers"
          item-key="id"
          :items="votes"
          :page.sync="page"
          :items-per-page="15"
          hide-default-footer
          :loading="loading"
          loading-text="Loading... Please wait"
          class="elevation-1 "
          @page-count="pageCount = $event"
          @click:row="onClickRow"
        />
        <div class="text-center pt-2">
          <v-pagination v-model="page" :length="pageCount" />
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang ="ts">
import Vue from 'vue'
import VoteAPI from 'assets/scripts/api/VoteAPI'
import Vote from '~/assets/scripts/model/Vote'

export default Vue.extend({
  async fetch () {
    const token = this.$store.state.Token.aToken
    const api = new VoteAPI(token)
    await api.getAll().then(t =>
      this.$store.commit('Votes/updateList', t)
    ).catch(
    )
    this.loading = false
  },
  data () {
    return {
      page: 1,
      pageCount: 0,
      headers: [
        { text: 'タイトル', value: 'title', width: '60%', align: 'start', sortable: false },
        { text: '作成者', value: 'createdBy.name', width: '25%' },
        { text: '期限', value: 'untilString', width: '15%' }
      ],
      loading: true
    }
  },
  computed: {
    votes () {
      return this.$store.state.Votes.list
    }

  },
  methods: {
    onClickRow (data:Vote) {
      this.$router.push('/voting/' + data.id.toString())
    }
  }
})
</script>
