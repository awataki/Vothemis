<template>
  <v-container
    class="fill-height"
    fluid
    @keyup.enter="login"
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

              <v-text-field
                id="password"
                v-model="password"
                label="パスワード"
                name="password"
                prepend-icon="mdi-lock"
                type="password"
              />
            </v-form>
          </v-card-text>
          <v-card-actions>
            <span v-if="err" class="error pa-1" style="color: white; border-radius: 5px;">{{ errMsg }}</span>
            <v-spacer />
            <v-btn color="primary" @click="login">
              ログイン
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import Vue from 'vue'
import LoginAPI from '~/assets/scripts/api/LoginAPI'
import UserAPI from '~/assets/scripts/api/UserAPI'

export default Vue.extend({
  data () {
    return {
      userName: '',
      password: '',
      err: false,
      errMsg: ''
    }
  },
  methods: {
    async login () {
      try {
        const api = new LoginAPI()
        const tokenPair = await api.login(this.userName, this.password)
        this.$store.commit('Token/updateAccessToken', tokenPair.aToken)

        const uApi = new UserAPI(tokenPair.aToken)
        const base64Payload = tokenPair.aToken.split('.')[1].replace(/-/g, '+').replace(/_/g, '/')
        const userId = JSON.parse(decodeURIComponent(escape(window.atob(base64Payload)))).user_id
        const user = await uApi.getBy(userId)
        this.$store.commit('LoginUser/login', user)

        await this.$router.push('/')
      } catch (e) {
        this.err = true
        switch (e.response.status) {
          case 404:
            this.errMsg = 'ユーザーまたはパスワードが間違っています'
            break
          default:
            this.errMsg = e.response.data
        }
      }
    }
  }
})
</script>
