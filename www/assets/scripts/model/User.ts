export default class User {
  get bio (): String {
    return this._bio
  }

  set bio (value: String) {
    this._bio = value
  }

  get name (): String {
    return this._name
  }

  set name (value: String) {
    this._name = value
  }

  get id (): Number {
    return this._id
  }

  set id (value: Number) {
    this._id = value
  }

    private _id:Number;
    private _name:String;
    private _bio:String;

    constructor (id:Number, name:String, bio:String) {
      this._id = id
      this._name = name
      this._bio = bio
    }
}
