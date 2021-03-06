package sh.awtk.vothemis.interfaces.repository

import sh.awtk.vothemis.dto.CandidateDto
import sh.awtk.vothemis.vo.CandidateId
import sh.awtk.vothemis.vo.QuestionId

interface ICandidateRepository {
    fun isParent(questionId: QuestionId, candidateId: CandidateId): Boolean
    fun replace(questionId: QuestionId, candidates: List<CandidateDto>): List<CandidateDto>
    fun deleteBy(questionId: QuestionId)
}