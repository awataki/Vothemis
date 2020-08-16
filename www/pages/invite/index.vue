<template>
  <v-container
    class="fill-height"
    fluid
    @keyup.enter="invite()"
  >
    <v-row
      align="center"
      justify="center"
    >
      <v-col
        cols="12"
        sm="8"
        md="4"
      >
        <v-dialog
          v-model="dialog"
          width="500"
        >
          <template v-slot:activator="{on,attr}">
            <v-card class="elevation-12 pa-4">
              <v-card-text>
                <v-form>
                  <v-text-field
                    v-model="userName"
                    label="ユーザー名"
                    name="login"
                    prepend-icon="mdi-account"
                    type="text"
                  />
                </v-form>
              </v-card-text>
              <v-card-actions>
                <span v-if="err" class="error pa-1" style="color: white; border-radius: 5px;">{{ errMsg }}</span>
                <v-spacer />
                <v-btn color="primary" v-bind="attr" @click="invite">
                  招待
                </v-btn>
              </v-card-actions>
            </v-card>
          </template>
          <v-card>
            <v-card-title class="headline grey lighten-2">
              招待リンク
            </v-card-title>

            <v-text-field
              v-model="inviteLink"
              class="mx-7 my-1"
              readonly
              append-outer-icon="mdi-link-variant"
              @click:append-outer="copyInviteLink"
            />

            <v-card-actions>
              <v-spacer />
              <v-btn
                color="primary"
                text
                @click="dialog = false"
              >
                閉じる
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-col>
    </v-row>
    <v-snackbar
      v-model="snackbar"
      timeout="1500"
    >
      招待リンクをコピーしました
      <template v-slot:action="{ attrs }">
        <v-btn
          color="white"
          text
          v-bind="attrs"
          @click="snackbar = false"
        >
          Close
        </v-btn>
      </template>
    </v-snackbar>
  </v-container>
</template>

<script lang="ts">
import Vue from 'vue'
import User from '~/assets/scripts/model/User'
import UserAPI from '~/assets/scripts/api/UserAPI'

export default Vue.extend({
  data () {
    return {
      userName: '',
      err: false,
      errMsg: '',
      inviteLink: '',
      dialog: false,
      snackbar: false
    }
  },
  methods: {
    async invite () {
      const token = this.$store.state.Token.aToken
      const api = new UserAPI(token)
      if (this.userName !== '') {
        const randomPass:String = this.genRandomString(this.getRandomArbitrary(20, 100))
        await api.create(new User(0, this.userName, randomPass, '')).then((res) => {
          // TODO urlをconfigでどうこうできるようにする。
          this.inviteLink = 'https://vothemis.awtk.sh/invite/' + res.id.toString() + '/?key=' + randomPass + '&name=' + res.name.toString()
          this.dialog = true
        }).catch(
          (e:any) => {
            this.err = true
            this.errMsg = e.response.data
          }
        )
      }
    },
    genRandomString (length:number) {
      const { randomBytes } = require('crypto')
      return randomBytes(length).reduce((p:any, i:any) => p + (i % 32).toString(32), '')
    },
    getRandomArbitrary (min:number, max:number) {
      return Math.random() * (max - min) + min
    },
    copyInviteLink () {
      const el = document.createElement('textarea')
      el.addEventListener('focusin', e => e.stopPropagation())
      el.value = this.inviteLink
      document.body.appendChild(el)
      el.select()
      document.execCommand('copy')
      document.body.removeChild(el)
      this.snackbar = true
    }
  }
})
</script>
