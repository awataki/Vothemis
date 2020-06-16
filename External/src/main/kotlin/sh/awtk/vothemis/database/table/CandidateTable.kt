package sh.awtk.vothemis.database.table

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.Column

object CandidateTable : LongIdTable("Candidate") {
    var questionID: Column<Long> = long("question_id")
    var discription: Column<String> = varchar("discription", 256)
    var numOfVote: Column<Long> = long("num_of_vote")
}

class CandidateEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<CandidateEntity>(CandidateTable)

    var questionID by CandidateTable.questionID
    var discription by CandidateTable.discription
    var numOfVote by CandidateTable.numOfVote
    var parentQuestion by QuestionEntity referencedOn CandidateTable.questionID
}
