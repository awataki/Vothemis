export const state = () => ({
  voted: {}
})

export const mutations = {
  updateVote (state:{voted:{ [key: string]: Number; }}, voted:{questionId:Number, candidateId:Number}) {
    const qId = voted.questionId.toString()
    state.voted[qId] = voted.candidateId
  }
}
