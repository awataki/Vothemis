import User from '~/assets/scripts/model/User'

export const state = () => ({
  id: 0,
  name: '',
  bio: ''
}
)

export const mutations = {
  login (state:User, login:User) {
    state.id = login.id
    state.name = login.name
    state.bio = login.bio
  },
  logout (state:User) {
    state.id = 0
    state.name = ''
    state.bio = ''
  }
}
