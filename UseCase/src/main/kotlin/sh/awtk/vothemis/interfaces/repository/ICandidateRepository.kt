package sh.awtk.vothemis.interfaces.repository

import sh.awtk.vothemis.dto.CandidateDto
import sh.awtk.vothemis.vo.QuestionId

interface ICandidateRepository {
    fun create(candidate: CandidateDto): CandidateDto
    fun findBy(Id: QuestionId): CandidateDto
    fun update(candidate: CandidateDto): CandidateDto
    fun delete(Id: QuestionId): Unit
}