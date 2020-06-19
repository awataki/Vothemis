package sh.awtk.vothemis.service

import sh.awtk.vothemis.dto.QuestionDto
import sh.awtk.vothemis.dto.validate
import sh.awtk.vothemis.exception.ForbiddenException
import sh.awtk.vothemis.exception.ObjectNotFoundExcepiton
import sh.awtk.vothemis.interfaces.repository.ICandidateRepository
import sh.awtk.vothemis.interfaces.repository.IQuestionRepository
import sh.awtk.vothemis.interfaces.repository.ITransaction
import sh.awtk.vothemis.interfaces.repository.IUserRepository
import sh.awtk.vothemis.interfaces.service.IQuestionService
import sh.awtk.vothemis.vo.QuestionId
import sh.awtk.vothemis.vo.UserId

class QuestionService(
    private val questionRepo: IQuestionRepository,
    private val candidateRepo: ICandidateRepository,
    private val userRepo: IUserRepository,
    private val transaction: ITransaction
) :
    IQuestionService {
    override suspend fun createNewQuestion(question: QuestionDto, userId: UserId): QuestionDto {
        question.validate()
        return transaction.run {
            question.createdBy =
                userRepo.findBy(userId) ?: throw ObjectNotFoundExcepiton("fail to find uer $userId")
            questionRepo.create(question)
                .also {
                    it.candidates = candidateRepo.replace(it.id, question.candidates)
                }
        }
    }

    override suspend fun getQuestion(id: QuestionId): QuestionDto {
        return transaction.run {
            questionRepo.findBy(id) ?: throw ObjectNotFoundExcepiton("fail to find question $id")
        }
    }

    override suspend fun getAllQuestions(): List<QuestionDto> {
        return transaction.run {
            questionRepo.findAll() ?: throw ObjectNotFoundExcepiton("No question found")
        }
    }

    override suspend fun updateQuestionData(question: QuestionDto, userId: UserId): QuestionDto {
        question.validate()
        return transaction.run {
            val prevQuestion = questionRepo.findBy(question.id)
            if (prevQuestion?.createdBy?.id?.value != userId.value) throw ForbiddenException("User id not match")
            question.createdBy = prevQuestion.createdBy
            questionRepo.update(question)?.also {
                it.candidates = candidateRepo.replace(it.id, question.candidates)
            } ?: throw ObjectNotFoundExcepiton("fail to find question ${question.id}")
        }
    }

    override suspend fun deleteQuestion(id: QuestionId, userId: UserId) {
        return transaction.run {
            val prevQuestion = questionRepo.findBy(id)
            if (prevQuestion?.createdBy?.id?.value != userId.value) throw ForbiddenException("User id not match")
            candidateRepo.deleteBy(id)
            questionRepo.delete(id) ?: throw ObjectNotFoundExcepiton("fail to find question $id")
        }
    }

}