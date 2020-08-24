export default class Candidate {
  get candidateId (): Number {
    return this._candidateId
  }

  set candidateId (value: Number) {
    this._candidateId = value
  }

  get questionId (): Number {
    return this._questionId
  }

  set questionId (value: Number) {
    this._questionId = value
  }

  get description (): String {
    return this._description
  }

  set description (value: String) {
    this._description = value
  }

  get numOfVote (): Number {
    return this._numOfVote
  }

  set numOfVote (value: Number) {
    this._numOfVote = value
  }

  get percentage (): Number {
    return this._percentage
  }

  set percentage (value: Number) {
    this._percentage = value
  }

  private _candidateId!:Number
  private _questionId!:Number
  private _description!:String
  private _numOfVote:Number = 0
  private _percentage:Number = 0

  constructor (candidateId:Number, questionId:Number, description:String, numOfVote:Number, percentage:Number) {
    this.candidateId = candidateId
    this.questionId = questionId
    this.description = description
    this.numOfVote = numOfVote
    this.percentage = percentage
  }
}
