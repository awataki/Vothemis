export default class TokenPair {
  get aToken (): String {
    return this._aToken
  }

  set aToken (value: String) {
    this._aToken = value
  }

  set rToken (value: String) {
    this._rToken = value
  }

  get rToken (): String {
    return this._rToken
  }

    private _aToken!:String
    private _rToken!:String

    constructor (a:String, r:String) {
      this.aToken = a
      this.rToken = r
    }
}
