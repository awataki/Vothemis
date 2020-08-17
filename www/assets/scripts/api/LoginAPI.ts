import axios from 'axios'
import BaseAPI from '~/assets/scripts/api/BaseAPI'
import TokenPair from '~/assets/scripts/model/TokenPair'

export default class LoginAPI extends BaseAPI {
  async login (userName:String, pass:String) {
    const res = await axios.post('/api/v1/login',
      {
        name: userName,
        password: pass
      }
    )
    const json = res.data
    return new TokenPair(
      json.access_token,
      json.refresh_token
    )
  }
}
