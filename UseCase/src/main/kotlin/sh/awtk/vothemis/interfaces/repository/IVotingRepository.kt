package sh.awtk.vothemis.interfaces.repository

import sh.awtk.vothemis.dto.VotingDto
import sh.awtk.vothemis.vo.CandidateId
import sh.awtk.vothemis.vo.NumOfVote
import sh.awtk.vothemis.vo.QuestionId

interface IVotingRepository {
    fun replace(votingDto: VotingDto)
    fun findBy(questionId: QuestionId): List<VotingDto>
    fun countBy(candidateId: CandidateId): NumOfVote
    fun deleteBy(questionId: QuestionId)
}