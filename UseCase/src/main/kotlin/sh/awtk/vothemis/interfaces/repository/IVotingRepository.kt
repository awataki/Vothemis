package sh.awtk.vothemis.interfaces.repository

import sh.awtk.vothemis.vo.CandidateId
import sh.awtk.vothemis.vo.NumOfVote
import sh.awtk.vothemis.vo.QuestionId
import sh.awtk.vothemis.vo.UserId

interface IVotingRepository {
    fun replace(questionId: QuestionId, userId: UserId, candidateId: CandidateId)
    fun countBy(candidateId: CandidateId): NumOfVote
    fun deleteBy(questionId: QuestionId)
}