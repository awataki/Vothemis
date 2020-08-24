<template>
  <v-col cols="12" class="ma-0 pa-0">
    <v-row align="center">
      <v-radio :value="candidate.candidateId" class="px-5 py-3" style="width: 100%;" :label="candidate.description" :disabled="voted" />
      <v-progress-linear
        v-if="voted"
        :value="candidate.percentage"
        class="mx-6 my-3 "
        height="20"
        dark
        color="secondary"
      >
        <span>{{ candidate.percentage }} % [{{ candidate.numOfVote }}]</span>
      </v-progress-linear>
    </v-row>
  </v-col>
</template>

<script lang="ts">
import Vue from 'vue'
import Candidate from '~/assets/scripts/model/Candidate'

export default Vue.extend({
  props: {
    candidate: {
      type: Candidate,
      required: true
    },
    voted: {
      type: Boolean,
      required: true
    }
  }
})
</script>
