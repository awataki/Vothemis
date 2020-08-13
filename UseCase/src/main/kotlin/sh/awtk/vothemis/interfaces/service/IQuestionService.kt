package sh.awtk.vothemis.interfaces.service

import sh.awtk.vothemis.dto.QuestionDto
import sh.awtk.vothemis.dto.VotingDto
import sh.awtk.vothemis.vo.QuestionId
import sh.awtk.vothemis.vo.UserId

interface IQuestionService {
    suspend fun createNewQuestion(question: QuestionDto, userId: UserId): QuestionDto
    suspend fun getQuestion(id: QuestionId): QuestionDto
    suspend fun getAllQuestions(): List<QuestionDto>
    suspend fun voting(votingDto: VotingDto)
    suspend fun updateQuestionData(question: QuestionDto, userId: UserId): QuestionDto
    suspend fun deleteQuestion(id: QuestionId, userId: UserId)
}