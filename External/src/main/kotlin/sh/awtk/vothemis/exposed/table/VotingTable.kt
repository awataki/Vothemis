package sh.awtk.vothemis.exposed.table

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.LongIdTable

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
}