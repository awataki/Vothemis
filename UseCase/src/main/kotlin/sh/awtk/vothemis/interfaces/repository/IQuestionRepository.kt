package sh.awtk.vothemis.interfaces.repository

import sh.awtk.vothemis.dto.QuestionDto
import sh.awtk.vothemis.vo.QuestionId

interface IQuestionRepository {
    fun create(question: QuestionDto): QuestionDto
    fun findBy(Id: QuestionId): QuestionDto
    fun findAll(): List<QuestionDto>
    fun update(question: QuestionDto): QuestionDto
    fun delete(Id: QuestionId): Unit
}