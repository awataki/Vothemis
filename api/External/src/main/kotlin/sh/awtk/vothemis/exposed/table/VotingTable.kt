package sh.awtk.vothemis.exposed.table

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.LongIdTable
import sh.awtk.vothemis.dto.VotingDto
import sh.awtk.vothemis.vo.CandidateId
import sh.awtk.vothemis.vo.QuestionId
import sh.awtk.vothemis.vo.UserId

object VotingTable : LongIdTable() {
    var questionId = reference("question_id", QuestionTable)
    var userId = reference("voted_by", UserTable)
    var candidateId = reference("candidate_id", CandidateTable)

    init {
        index(true, questionId, userId)
    }
}

class VotingEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<VotingEntity>(VotingTable)

    var questionId by QuestionEntity referencedOn VotingTable.questionId
    var userId by UserEntity referencedOn VotingTable.userId
    var candidateId by CandidateEntity referencedOn VotingTable.candidateId

    fun toDto(): VotingDto {
        return VotingDto(
            questionId = QuestionId(questionId.id.value),
            userId = UserId(userId.id.value),
            candidateId = CandidateId(candidateId.id.value)
        )
    }
}