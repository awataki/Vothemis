<template>
  <v-col cols="12" class="ma-0 pa-0">
    <v-row align="center">
      <v-radio :value="candidate.candidateId" class="px-5 py-3" style="width: 100%;" :label="candidate.description" />
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
    }
  }
})
</script>
