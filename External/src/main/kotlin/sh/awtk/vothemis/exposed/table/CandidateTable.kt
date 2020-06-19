package sh.awtk.vothemis.exposed.table

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.Column
import sh.awtk.vothemis.dto.CandidateDto
import sh.awtk.vothemis.vo.CandidateDescription
import sh.awtk.vothemis.vo.CandidateId
import sh.awtk.vothemis.vo.QuestionId

object CandidateTable : LongIdTable("Candidate") {
    var questionID: Column<EntityID<Long>> = reference("question_id", QuestionTable)
    var description: Column<String> = varchar("description", 256)
}

class CandidateEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<CandidateEntity>(CandidateTable)

    var questionID by QuestionEntity referencedOn CandidateTable.questionID
    var description by CandidateTable.description
    var parentQuestion by QuestionEntity referencedOn CandidateTable.questionID

    fun toDto(): CandidateDto {
        return CandidateDto(
            id = CandidateId(id.value),
            questionId = QuestionId(questionID.id.value),
            description = CandidateDescription(description)
        )
    }
}
