import Vote from '~/assets/scripts/model/Vote'

export const state = () => ({
  list: []
})

export const mutations = {
  updateList (state:{list:Vote[]}, list:Vote[]) {
    state.list = list
  }
}
