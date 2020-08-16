export default class User {
  get id (): Number {
    return this._id
  }

  set id (value: Number) {
    this._id = value
  }

  get name (): String {
    return this._name
  }

  set name (value: String) {
    this._name = value
  }

  get pass (): String {
    return this._pass
  }

  set pass (value: String) {
    this._pass = value
  }

  get bio (): String {
    return this._bio
  }

  set bio (value: String) {
    this._bio = value
  }

  private _id!:Number;
  private _pass:String = '';
  private _name!:String;
  private _bio!:String;

  constructor (id:Number, name:String, pass:String, bio:String) {
    this.id = id
    this.name = name
    this.pass = pass
    this.bio = bio
  }
}
