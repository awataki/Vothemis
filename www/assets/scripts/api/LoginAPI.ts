import axios from 'axios'
import TokenPair from '~/assets/scripts/model/TokenPair'

export default class LoginAPI {
  async login (userName:String, pass:String) {
    const res = await axios.post('/api/v1/login',
      {
        name: userName,
        password: pass
      },
      {
        headers: { 'Content-Type': 'application/json' }
      }
    )
    const json = res.data
    return new TokenPair(
      json.access_token,
      json.refresh_token
    )
  }
}
