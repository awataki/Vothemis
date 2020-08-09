<template>
  <v-col cols="12" md="6" xl="3">
    <v-row align="center" class="ma-2">
      <v-radio :value="candidate.candidateId" class="" style="width: 100%;">
        <template v-slot:label>
          <v-progress-linear height="30px">
            <strong>
              {{ candidate.description }}
            </strong>
          </v-progress-linear>
        </template>
      </v-radio>
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
