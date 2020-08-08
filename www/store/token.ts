export const state = () => ({
  aToken: '',
  rToken: ''
})

export const mutations = {
  updateAccessToken (state: { aToken: String, rToken:String }, token: String) {
    state.aToken = token
  },
  removeAccessToken (state: { aToken: String, rToken:String }) {
    state.aToken = ''
  }
}
