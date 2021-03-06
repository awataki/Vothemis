import axios from 'axios'
import BaseAPI from '~/assets/scripts/api/BaseAPI'
import Vote from '~/assets/scripts/model/Vote'
import Candidate from '~/assets/scripts/model/Candidate'
import User from '~/assets/scripts/model/User'

export default class VoteAPI extends BaseAPI {
  async getAll ():Promise<Vote[]> {
    const res = await axios.get('/api/v1/question/list', this.createHeaderWithToken())
    const json = res.data
    return json.map((v:any) => this.parseVote(v))
  }

  async findBy (id:Number) {
    const res = await axios.get('/api/v1/question/' + id.toString(), this.createHeaderWithToken())
    const json = res.data
    return this.parseVote(json)
  }

  async voting (id:Number, candidateId: Number) {
    const res = await axios.post('/api/v1/question/' + id.toString(), { candidate_id: candidateId.toString() }, this.createHeaderWithToken())
    return res.data
  }

  async create (newVote:Vote) {
    const res = await axios.post('/api/v1/question/', { title: newVote.title, sentence: newVote.sentence, available_candidate: newVote.availableCandidate.map((c) => { return { description: c.description } }), until: newVote.until.toISOString() }, this.createHeaderWithToken())
    const json = res.data
    return this.parseVote(json)
  }

  private parseVote (json:any):Vote {
    const candidates:Candidate[] = json.available_candidate.map((c:any) =>
      new Candidate(
        +c.candidate_id,
        +c.question_id,
        c.description,
        +c.num_of_vote,
        isNaN((+c.num_of_vote / +json.total) * 100) ? 0 : (+c.num_of_vote / +json.total) * 100
      )
    )
    return new Vote(
      +json.id,
      json.title,
      json.sentence,
      candidates,
      new Date(json.until),
      new User(
        +json.created_by.id,
        json.created_by.name,
        '',
        json.created_by.bio
      )
    )
  }
}
