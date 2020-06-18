package sh.awtk.vothemis.presenter

import sh.awtk.vothemis.dto.toResponse
import sh.awtk.vothemis.interfaces.service.IQuestionService
import sh.awtk.vothemis.viewmodel.QuestionRequest
import sh.awtk.vothemis.viewmodel.QuestionResponse
import sh.awtk.vothemis.viewmodel.toDto
import sh.awtk.vothemis.vo.QuestionId
import sh.awtk.vothemis.vo.UserId

class QuestionPresenter(private val questionService: IQuestionService) {
    suspend fun createNewQuestion(questionRequest: QuestionRequest, userId: Long): QuestionResponse =
        questionService.createNewQuestion(questionRequest.toDto(), UserId(userId)).toResponse()

    suspend fun getQuestionById(questionId: Long): QuestionResponse =
        questionService.getQuestion(QuestionId(questionId)).toResponse()


    suspend fun updateSpecificQuestion(questionRequest: QuestionRequest, userId: Long): QuestionResponse =
        questionService.updateQuestionData(questionRequest.toDto(), UserId(userId)).toResponse()

    suspend fun deleteSpecificQuestion(questionId: Long, userId: Long) =
        questionService.deleteQuestion(QuestionId(questionId), UserId(userId))

    suspend fun getAllQuestionList(): List<QuestionResponse> =
        questionService.getAllQuestions().map { it.toResponse() }
}