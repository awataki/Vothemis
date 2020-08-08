export default class TokenPair {
  set rToken (value: String) {
    this._rToken = value
  }

  get rToken (): String {
    return this._rToken
  }

  get aToken (): String {
    return this._aToken
  }

  set aToken (value: String) {
    this._aToken = value
  }

    private _aToken:String
    private _rToken:String

    constructor (a:String, r:String) {
      this._aToken = a
      this._rToken = r
    }
}
