<template>
  <v-container
    class="fill-height"
    fluid
  >
    <v-row
      align="start"
      justify="center"
    >
      <v-col
        cols="12"
        md="8"
      >
        <v-card class="elevation-1 pa-4">
          <v-card-text>
            <h1>{{ detail.sentence }}</h1>
            <v-form>
              <v-radio-group v-model="vote" :mandatory="false">
                <v-row>
                  <Candidate v-for="i in detail.availableCandidate" :key="i.candidateId" :candidate="i" />
                </v-row>
              </v-radio-group>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer />
            <v-btn color="primary" @click="voting">
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

export default Vue.extend({
  components: {
    Candidate: () => import('~/components/Candidate.vue')
  },
  async asyncData ({ store, params }) {
    const api = new VoteAPI(store.state.Token.aToken)
    const res = await api.findBy(+params.id)
    return { detail: res }
  },
  data () {
    return {
      vote: 0
    }
  },
  mounted () {
    console.log(this.$store.state.Voted.list)
    this.vote = this.$store.state.Voted.list[this.$route.params.id.toString()]
  },
  methods: {
    async voting () {
      const questionId:Number = +this.$route.params.id
      const candidateId:Number = this.vote
      const api = new VoteAPI(this.$store.state.Token.aToken)
      await api.Voting(questionId, candidateId).then(

      ).catch(e =>
        console.log(e)
      )
      this.$store.commit('Voted/updateVote', { questionId, candidateId })
      console.log(this.$store.state.Voted.list)
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
