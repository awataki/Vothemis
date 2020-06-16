package sh.awtk.vothemis.database.table

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.joda.time.DateTime

object QuestionTable : LongIdTable("questions") {
    var title: Column<String> = varchar("title", 256)
    var sentence: Column<String> = varchar("sentence", 256)
    var until: Column<DateTime> = date("until")
    var createdBy: Column<EntityID<Long>> = reference("created_by", UserTable)
}

class QuestionEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<QuestionEntity>(QuestionTable)

    var title by QuestionTable.title
    var sentence by QuestionTable.sentence
    val avaliable_candidate by CandidateEntity referrersOn CandidateTable.questionID
    var until by QuestionTable.until
    var created_by by UserEntity referencedOn QuestionTable.createdBy
}