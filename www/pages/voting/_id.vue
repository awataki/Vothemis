<template>
  <v-container
    class=" py-0"
    fluid
  >
    <v-row>
      <v-toolbar class="elevation-1 mb-6">
        <v-btn icon class="mx-3" @click="$router.back()">
          <v-icon>mdi-arrow-left</v-icon>
        </v-btn>
        <h3>{{ detail.title }}</h3>
      </v-toolbar>
    </v-row>
    <v-row
      align="start"
      justify="center"
    >
      <v-col
        cols="12"
        md="8"
        lg="6"
        xl="4"
      >
        <v-card class="elevation-5 pa-4">
          <v-card-title>{{ detail.sentence }}</v-card-title>
          <v-card-text>
            <v-form>
              <v-radio-group v-model="vote" :mandatory="false">
                <v-row>
                  <Candidate v-for="i in detail.availableCandidate" :key="i.candidateId" :candidate="i" :voted=" voted||isOwner" />
                </v-row>
              </v-radio-group>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer />
            <v-btn :disabled="voted||isOwner" color="primary" @click="voting">
              投票
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
<script lang="ts">
import Vue from 'vue'
import VoteAPI from '~/assets/scripts/api/VoteAPI'
import Vote from '~/assets/scripts/model/Vote'
import User from '~/assets/scripts/model/User'

export default Vue.extend({
  components: {
    Candidate: () => import('~/components/Candidate.vue')
  },
  async asyncData ({ store, params }) {
    try {
      const api = new VoteAPI(store.state.Token.aToken)
      const res = await api.findBy(+params.id)
      return { detail: res }
    } catch (e) {
      console.log(e.body)
    }
  },
  data () {
    return {
      vote: 0,
      voted: false,
      progress: false,
      detail: new Vote(0, '', '', [], new Date(), new User(0, '', '', ''))
    }
  },
  computed: {
    isOwner () {
      // @ts-ignore
      return this.detail.createdBy.id === this.$store.state.LoginUser.id
    }
  },
  mounted () {
    this.vote = this.$store.state.Voted.voted[this.$route.params.id.toString()]
    if (this.vote != null) {
      this.voted = true
    }
  },
  methods: {
    async voting () {
      this.progress = true
      const questionId:Number = +this.$route.params.id
      const candidateId:Number = this.vote

      const api = new VoteAPI(this.$store.state.Token.aToken)
      await api.voting(questionId, candidateId).then(
      ).catch(
      )
      this.$store.commit('Voted/updateVote', { questionId, candidateId })

      this.detail = await api.findBy(+this.$route.params.id)
      this.voted = true
    }
  },
  validate ({ params }) {
    return /^\d+$/.test(params.id)
  }
})
</script>

<style>
.v-radio {
  margin-bottom: 0 !important;
}
</style>
