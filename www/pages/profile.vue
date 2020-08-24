<template>
  <v-container fluid style="height: 100%;" class="ma-0 py-0">
    <v-row align="center" justify="center">
      <v-col
        cols="12"
        md="8"
        class="my-2"
      >
        <v-card>
          <v-card-title>
            {{ user.name }}
            <v-spacer />
            <v-dialog
              v-model="dialog"
              width="500"
            >
              <template v-slot:activator="{on,attr}">
                <v-btn
                  color="primary"
                  outlined
                  v-bind="attr"
                  v-on="on"
                >
                  編集
                </v-btn>
              </template>
              <v-card>
                <v-card>
                  <v-card-title>
                    ユーザー情報の編集
                  </v-card-title>
                  <v-card-text>
                    <v-form v-model="valid">
                      <v-text-field v-model="userName" label="ユーザー名" />
                      <v-text-field v-model="pass" label="パスワード(変更する場合のみ入力)" />
                      <v-textarea v-model="bio" label="プロフィール" />
                      <v-spacer />
                      <v-text-field v-model="passConfirm" label="現在のパスワード" :rules="[rules.required]" type="password" />
                    </v-form>
                  </v-card-text>
                  <v-card-actions>
                    <span v-if="err" class="error pa-1" style="color: white; border-radius: 5px;">{{ errMsg }}</span>
                    <v-spacer />
                    <v-btn color="primary" :disabled="!valid" @click="updateUser">
                      更新
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-card>
            </v-dialog>
          </v-card-title>
          <v-card-text>
            {{ user.bio }}
          </v-card-text>
          <v-card-title>
            作成した投票
          </v-card-title>
          <v-card-text>
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
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import Vue from 'vue'
import UserAPI from '~/assets/scripts/api/UserAPI'
import LoginAPI from '~/assets/scripts/api/LoginAPI'
import User from '~/assets/scripts/model/User'
import VoteAPI from '~/assets/scripts/api/VoteAPI'
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
      dialog: false,
      userName: '',
      pass: '',
      bio: '',
      passConfirm: '',
      valid: false,
      rules: {
        required: (value:any) => !!value || '入力が必須です'
      },
      err: false,
      errMsg: '',
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
    user () {
      return this.$store.state.LoginUser
    },
    votes () {
      if (this.$store.state.Votes.list[0] != null && this.$store.state.Votes.list[0].createdBy != null) {
        return this.$store.state.Votes.list.filter((it:Vote) => this.user.id === it.createdBy.id)
      } else {
        return undefined
      }
    }
  },
  created () {
    this.userName = this.user.name
    this.pass = ''
    this.bio = this.user.bio
  },
  methods: {
    async updateUser () {
      this.err = false
      try {
        const loginAPI = new LoginAPI()
        const token = await loginAPI.login(this.user.name, this.passConfirm)
        this.$store.commit('Token/updateAccessToken', token.aToken)
        const api = new UserAPI(this.$store.state.Token.aToken)
        const user = await api.update(new User(this.user.id, this.userName, this.pass, this.bio))
        this.$store.commit('LoginUser/login', user)
        this.dialog = false
      } catch (e) {
        this.err = true
        if (e.response.body === '') {
          this.errMsg = '内部エラーが発生しました'
        } else {
          this.errMsg = e.response.body
        }
      } finally {
        this.passConfirm = ''
      }
    },

    onClickRow (data:Vote) {
      this.$router.push('/voting/' + data.id.toString())
    }
  }
})
</script>
