<template>
  <v-container
    class="fill-height"
    fluid
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

<script>
import LoginAPI from '~/assets/scripts/api/LoginAPI'

export default {
  data () {
    return {
      userName: '',
      password: ''
    }
  },
  methods: {
    async login () {
      const api = new LoginAPI()
      const tokenPair = await api.login(this.userName, this.password).catch(e => console.log(e.response.status))
      if (tokenPair !== undefined) {
        this.$store.commit('token/updateAccessToken', tokenPair.aToken)
        await this.$router.push('/')
      }
    }
  }
}
</script>
