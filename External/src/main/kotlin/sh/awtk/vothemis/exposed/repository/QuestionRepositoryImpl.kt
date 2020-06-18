package sh.awtk.vothemis.exposed.repository

import org.joda.time.DateTime
import sh.awtk.vothemis.dto.QuestionDto
import sh.awtk.vothemis.exception.ObjectNotFoundExcepiton
import sh.awtk.vothemis.exposed.table.QuestionEntity
import sh.awtk.vothemis.exposed.table.UserEntity
import sh.awtk.vothemis.interfaces.repository.IQuestionRepository
import sh.awtk.vothemis.vo.QuestionId

class QuestionRepositoryImpl : IQuestionRepository {
    override fun create(question: QuestionDto): QuestionDto = QuestionEntity.new {
        title = question.title.value
        sentence = question.sentence.value
        until = DateTime(question.until)
        created_by = UserEntity.findById(question.createdBy!!.id.value)
            ?: throw ObjectNotFoundExcepiton("fail to find created by user")
    }.toDto()

    override fun findBy(Id: QuestionId): QuestionDto? = QuestionEntity.findById(Id.value)?.toDto()

    override fun findAll(): List<QuestionDto>? = QuestionEntity.all().map { it.toDto() }

    override fun update(question: QuestionDto): QuestionDto? = QuestionEntity.findById(question.id.value)?.also {
        it.title = question.title.value
        it.sentence = question.sentence.value
        it.until = DateTime(question.until)
    }?.toDto()

    override fun delete(Id: QuestionId): Unit? = QuestionEntity.findById(Id.value)?.delete()
}