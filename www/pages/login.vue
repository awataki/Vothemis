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
      const api = new LoginAPI()
      if (this.userName !== '' && this.password !== '') {
        const tokenPair = await api.login(this.userName, this.password).catch(
          (e:any) => {
            this.err = true
            switch (e.response.status) {
              case 404:
                this.errMsg = 'ユーザーまたはパスワードが間違っています'
            }
          }
        )
        if (tokenPair !== undefined) {
          this.$store.commit('Token/updateAccessToken', tokenPair.aToken)
          await this.$router.push('/')
        }
      }
    }
  }
})
</script>
