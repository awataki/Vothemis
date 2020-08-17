export const state = () => ({
  voted: {}
})

export const mutations = {
  updateVote (state:{list:{ [key: string]: Number; }}, voted:{questionId:Number, candidateId:Number}) {
    const qId = voted.questionId.toString()
    state.list[qId] = voted.candidateId
  }
}
