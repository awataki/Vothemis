package sh.awtk.vothemis.exposed.repository

import sh.awtk.vothemis.dto.CandidateDto
import sh.awtk.vothemis.exception.ObjectNotFoundExcepiton
import sh.awtk.vothemis.exposed.table.CandidateEntity
import sh.awtk.vothemis.exposed.table.CandidateTable
import sh.awtk.vothemis.exposed.table.QuestionEntity
import sh.awtk.vothemis.interfaces.repository.ICandidateRepository
import sh.awtk.vothemis.vo.QuestionId

class CandidateRepositoryImpl : ICandidateRepository {
    override fun replace(questionId: QuestionId, candidates: List<CandidateDto>): List<CandidateDto> {
        CandidateEntity.find { CandidateTable.questionID eq questionId.value }.also {
            if (!it.empty()) it.single().delete()
        }
        return candidates.map {
            CandidateEntity.new {
                this.questionID = QuestionEntity.findById(questionId.value)
                    ?: throw ObjectNotFoundExcepiton("fail to find Question $questionId")
                this.description = it.description.value
                this.numOfVote = it.numOfVote.value
            }.toDto()
        }
    }

    override fun deleteBy(questionId: QuestionId) {
        CandidateEntity.find { CandidateTable.questionID eq questionId.value }.also {
            if (!it.empty()) it.single().delete()
        }
    }

}