<template>
  <v-container fluid style="height: 100%;" class="ma-0 py-0">
    <v-row justify="center" align="center" style="height: 100%;">
      <v-col cols="12" class="ma-0 py-0">
        <v-row align="center" justify="center">
          <v-col
            cols="12"
            md="8"
            lg="6"
            xl="4"
            class="my-2"
          >
            <v-card>
              <v-card-title>
                ようこそ
              </v-card-title>
              <v-card-text>
                あなたのパスワードを決めてください。
                <v-form v-model="valid">
                  <v-text-field
                    v-model="password"
                    :type="showPass ? 'text' : 'password'"
                    :rules="[rules.required]"
                    label="パスワード"
                  />
                  <v-text-field
                    v-model="confirm"
                    :type="showConfirm ? 'text' : 'password'"
                    :rules="[v => v === password || 'パスワードが一致していません']"
                    label="確認のために再度入力してください"
                  />
                </v-form>
              </v-card-text>
              <v-card-actions>
                <span v-if="err" class="error pa-1" style="color: white; border-radius: 5px;">{{ errMsg }}</span>
                <v-spacer />
                <v-btn color="primary" :disabled="!valid" @click="activate()">
                  パスワードを設定
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import Vue from 'vue'
import UserAPI from '~/assets/scripts/api/UserAPI'
import LoginAPI from '~/assets/scripts/api/LoginAPI'
import User from '~/assets/scripts/model/User'

export default Vue.extend({
  data () {
    return {
      password: '',
      showPass: false,
      confirm: '',
      showConfirm: false,
      valid: false,
      err: false,
      errMsg: '',
      rules: {
        required: (value:any) => !!value || '入力が必須です'
      }
    }
  },
  methods: {
    async activate () {
      const id = this.$route.params.id
      const name = this.$route.query.name
      const key = this.$route.query.key
      if (typeof key === 'string' && typeof name === 'string') {
        try {
          const lApi = new LoginAPI()
          await lApi.login(name, key)
            .then(res =>
              this.$store.commit('Token/updateAccessToken', res.aToken)
            )
          const uApi = new UserAPI(this.$store.state.Token.aToken)
          const user:User | void = await uApi.getBy(+id)
          user.pass = this.password
          await uApi.update(user)
          await this.$router.push('/')
        } catch (e) {
          // wrap login errMsg
          if (e.response.data === 'ユーザー名またはパスワードが間違っています') {
            this.err = true
            this.errMsg = '無効な招待リンクです'
          } else {
            this.err = true
            this.errMsg = e.response.data
          }
        }
      }
    }
  }
})
</script>
