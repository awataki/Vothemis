package sh.awtk.vothemis.interfaces.service

import sh.awtk.vothemis.dto.QuestionDto
import sh.awtk.vothemis.vo.QuestionId

interface IQuestionService {
    fun createNewQuestion(question: QuestionDto): QuestionDto
    fun getQuestion(id: QuestionId): QuestionDto
    fun getAllQuestions(): List<QuestionDto>
    fun updateQuestionData(question: QuestionDto): QuestionDto
    fun deleteQuestion(id: QuestionId): Unit
}