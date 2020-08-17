import User from '~/assets/scripts/model/User'
import Candidate from '~/assets/scripts/model/Candidate'

export default class Vote {
  get untilString (): String {
    return this._untilString
  }

  set untilString (value: String) {
    this._untilString = value
  }

  get id (): Number {
    return this._id
  }

  set id (value: Number) {
    this._id = value
  }

  get title (): String {
    return this._title
  }

  set title (value: String) {
    this._title = value
  }

  get sentence (): String {
    return this._sentence
  }

  set sentence (value: String) {
    this._sentence = value
  }

  get availableCandidate (): Candidate[] {
    return this._availableCandidate
  }

  set availableCandidate (value:Candidate[]) {
    this._availableCandidate = value
  }

  get until (): Date {
    return this._until
  }

  set until (value: Date) {
    this._until = value
  }

  get createdBy (): User {
    return this._createdBy
  }

  set createdBy (value: User) {
    this._createdBy = value
  }

  private _id! :Number
  private _title!:String
  private _sentence!:String
  private _availableCandidate!:Candidate[]
  private _until!:Date
  private _untilString!:String
  private _createdBy!: User

  constructor (id:Number, title:string, sentence:string, availableCandidate:Candidate[], until:Date, createdBy:User) {
    this.id = id
    this.title = title
    this.sentence = sentence
    this.availableCandidate = availableCandidate
    this.until = until
    this.untilString = until.getMonth() + 1 + '/' + until.getDate() + ' ' + ('00' + until.getHours()).slice(-2) + ':' + ('00' + until.getMinutes()).slice(-2)
    this.createdBy = createdBy
  }
}
