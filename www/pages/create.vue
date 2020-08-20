<template>
  <v-container
    fluid
  >
    <v-row
      align="start"
      justify="center"
    >
      <v-col
        cols="12"
        sm="8"
        md="4"
      >
        <v-card class="elevation-2 pa-4">
          <v-card-text>
            <v-form>
              <v-text-field
                v-model="title"
                label="タイトル"
                :rules="[rules.required]"
              />
              <v-text-field
                v-model="sentence"
                label="質問内容"
                :rules="[rules.required]"
              />
              <v-row class="mx-0">
                <v-spacer />
                <v-btn icon @click="incrementCandidate">
                  <v-icon>mdi-plus</v-icon>
                </v-btn>
                <v-btn icon @click="decrementCandidate">
                  <v-icon>mdi-minus</v-icon>
                </v-btn>
              </v-row>
              <div v-for="(i,index) in candidates" :key="index">
                <v-text-field v-model="i.title" :label="'候補' + (index+1).toString()" />
              </div>
              <v-row>
                <v-col>
                  <v-menu
                    v-model="dateMenu"
                    :close-on-content-click="false"
                    :nudge-right="40"
                    transition="scale-transition"
                    offset-y
                    absolute
                    min-width="290px"
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <v-text-field
                        v-model="date"
                        label="締切日時"
                        prepend-icon="mdi-calendar"
                        readonly
                        v-bind="attrs"
                        v-on="on"
                      />
                    </template>
                    <v-date-picker v-model="date" @input="dateMenu = false" />
                  </v-menu>
                </v-col>
                <v-col>
                  <v-menu
                    ref="menu"
                    v-model="timeMenu"
                    :close-on-content-click="false"
                    :nudge-right="40"
                    :return-value.sync="time"
                    transition="scale-transition"
                    offset-y
                    absolute
                    max-width="290px"
                    min-width="290px"
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <v-text-field
                        v-model="time"
                        prepend-icon="mdi-clock"
                        readonly
                        v-bind="attrs"
                        v-on="on"
                      />
                    </template>
                    <v-time-picker
                      v-if="timeMenu"
                      v-model="time"
                      full-width
                      ampm-in-title
                      @click:minute="$refs.menu.save(time)"
                    />
                  </v-menu>
                </v-col>
              </v-row>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <span v-if="err" class="error pa-1" style="color: white; border-radius: 5px;">{{ errMsg }}</span>
            <v-spacer />
            <v-btn color="primary" @click="onSubmit">
              作成
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
import Candidate from '~/assets/scripts/model/Candidate'
export default Vue.extend({
  data () {
    return {
      title: '',
      sentence: '',
      dateMenu: false,
      date: new Date().toISOString().substr(0, 10),
      timeMenu: false,
      time: new Date().toISOString().substr(11, 5),
      err: false,
      errMsg: '',
      candidates: [{ title: '' }, { title: '' }, { title: '' }],
      rules: {
        required: (value:any) => !!value || '入力が必須です'
      }
    }
  },
  methods: {
    onSubmit () {
      const candidateArray = this.candidates.map(it => it.title)
      const api = new VoteAPI(this.$store.state.Token.aToken)
      const vote = new Vote(
        0,
        this.title,
        this.sentence,
        candidateArray.map(it => new Candidate(0, 0, it, 0)),
        new Date(this.date + 'T' + this.time),
        this.$store.state.LoginUser
      )
      api.create(vote).catch((e) => {
        this.err = true
        this.errMsg = e.response.data
      })
    },
    incrementCandidate () {
      this.candidates.push({ title: '' })
    },
    decrementCandidate () {
      this.candidates.pop()
    }
  }
})
</script>
