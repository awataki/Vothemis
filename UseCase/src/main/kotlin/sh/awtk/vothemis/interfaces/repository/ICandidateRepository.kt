package sh.awtk.vothemis.interfaces.repository

import sh.awtk.vothemis.dto.CandidateDto
import sh.awtk.vothemis.vo.QuestionId

interface ICandidateRepository {
    fun replace(questionId: QuestionId, candidates: List<CandidateDto>): List<CandidateDto>
}