package sh.awtk.vothemis.service

import sh.awtk.vothemis.dto.QuestionDto
import sh.awtk.vothemis.dto.validate
import sh.awtk.vothemis.exception.ForbiddenException
import sh.awtk.vothemis.exception.ObjectNotFoundExcepiton
import sh.awtk.vothemis.interfaces.repository.*
import sh.awtk.vothemis.interfaces.service.IQuestionService
import sh.awtk.vothemis.vo.CandidateId
import sh.awtk.vothemis.vo.QuestionId
import sh.awtk.vothemis.vo.UserId

class QuestionService(
    private val questionRepo: IQuestionRepository,
    private val candidateRepo: ICandidateRepository,
    private val votingRepo: IVotingRepository,
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
            questionRepo.findBy(id)?.addNumOfVote() ?: throw ObjectNotFoundExcepiton("fail to find question $id")
        }
    }

    override suspend fun getAllQuestions(): List<QuestionDto> {
        return transaction.run {
            questionRepo.findAll()?.map { it.addNumOfVote() } ?: throw ObjectNotFoundExcepiton("No question found")
        }
    }

    override suspend fun voting(questionId: QuestionId, userId: UserId, candidateId: CandidateId) {
        return transaction.run {
            votingRepo.replace(questionId, userId, candidateId)
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
            votingRepo.deleteBy(id)
            candidateRepo.deleteBy(id)
            questionRepo.delete(id) ?: throw ObjectNotFoundExcepiton("fail to find question $id")
        }
    }

    private fun QuestionDto.addNumOfVote(): QuestionDto {
        return this.also {
            it.candidates.map { candidate ->
                candidate.numOfVote = votingRepo.countBy(candidate.id)
            }
        }
    }
}