package sh.awtk.vothemis.dto

import sh.awtk.vothemis.vo.CandidateId
import sh.awtk.vothemis.vo.QuestionId
import sh.awtk.vothemis.vo.UserId

data class VotingDto(
    val questionId: QuestionId,
    val userId: UserId,
    val candidateId: CandidateId
)