import axios from 'axios'
import User from '~/assets/scripts/model/User'
import BaseAPI from '~/assets/scripts/api/BaseAPI'

export default class UserAPI extends BaseAPI {
  async getBy (userId: number): Promise<User> {
    const res = await axios.get('/api/v1/user/' + userId.toString(), this.createHeader())
    return this.parseUser(res.data)
  }

  async create (user: User) :Promise<User> {
    const res = await axios.post('/api/v1/user/create', user, this.createHeaderWithToken())
    return this.parseUser(res.data)
  }

  async update (user:User) :Promise<User> {
    const res = await axios.put('/api/v1/user/' + user.id.toString, this.createHeaderWithToken())
    return this.parseUser(res.data)
  }

  private parseUser (json:any):User {
    return new User(
      +json.id,
      json.name,
      json.bio
    )
  }
}
