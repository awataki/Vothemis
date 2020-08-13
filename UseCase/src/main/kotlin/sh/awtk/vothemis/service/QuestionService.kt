package sh.awtk.vothemis.service

import sh.awtk.vothemis.dto.QuestionDto
import sh.awtk.vothemis.dto.VotingDto
import sh.awtk.vothemis.dto.validate
import sh.awtk.vothemis.exception.BadRequestException
import sh.awtk.vothemis.exception.ForbiddenException
import sh.awtk.vothemis.exception.ObjectNotFoundException
import sh.awtk.vothemis.interfaces.repository.*
import sh.awtk.vothemis.interfaces.service.IQuestionService
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
                userRepo.findBy(userId) ?: throw ObjectNotFoundException("fail to find uer $userId")
            questionRepo.create(question)
                .also {
                    it.candidates = candidateRepo.replace(it.id, question.candidates)
                }
        }
    }

    override suspend fun getQuestion(id: QuestionId): QuestionDto {
        return transaction.run {
            questionRepo.findBy(id)?.addNumOfVote() ?: throw ObjectNotFoundException("fail to find question $id")
        }
    }

    override suspend fun getAllQuestions(): List<QuestionDto> {
        return transaction.run {
            questionRepo.findAll()?.map { it.addNumOfVote() } ?: throw ObjectNotFoundException("No question found")
        }
    }

    override suspend fun voting(votingDto: VotingDto) {
        return transaction.run {
            if (!candidateRepo.isParent(
                    votingDto.questionId,
                    votingDto.candidateId
                )
            ) throw BadRequestException("wrong question id")
            votingRepo.replace(votingDto)
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
            } ?: throw ObjectNotFoundException("fail to find question ${question.id}")
        }
    }

    override suspend fun deleteQuestion(id: QuestionId, userId: UserId) {
        return transaction.run {
            val prevQuestion = questionRepo.findBy(id)
            if (prevQuestion?.createdBy?.id?.value != userId.value) throw ForbiddenException("User id not match")
            votingRepo.deleteBy(id)
            candidateRepo.deleteBy(id)
            questionRepo.delete(id) ?: throw ObjectNotFoundException("fail to find question $id")
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