import { AxiosRequestConfig } from 'axios'
export default class BaseAPI {
  _token:String
  createHeader () :AxiosRequestConfig {
    return { headers: { 'Content-Type': 'application/json' } }
  }

  createHeaderWithToken () :AxiosRequestConfig {
    return { headers: { 'Content-Type': 'application/json', Authorization: 'bearer ' + this._token } }
  }

  constructor (token:String = '') {
    this._token = token
  }
}
