package sh.awtk.vothemis.exposed.repository

import org.jetbrains.exposed.sql.and
import sh.awtk.vothemis.exception.ObjectNotFoundExcepiton
import sh.awtk.vothemis.exposed.table.*
import sh.awtk.vothemis.interfaces.repository.IVotingRepository
import sh.awtk.vothemis.vo.CandidateId
import sh.awtk.vothemis.vo.NumOfVote
import sh.awtk.vothemis.vo.QuestionId
import sh.awtk.vothemis.vo.UserId

class VotingRepositoryImpl : IVotingRepository {
    override fun replace(questionId: QuestionId, userId: UserId, candidateId: CandidateId) {
        VotingEntity.find {
            (VotingTable.questionId eq questionId.value) and
                    (VotingTable.userId eq userId.value)
        }.let {
            if (!it.empty()) it.single().delete()
        }
        VotingEntity.new {
            this.questionId = QuestionEntity.findById(questionId.value)
                ?: throw  ObjectNotFoundExcepiton("fail to find question $questionId")
            this.userId = UserEntity.findById(userId.value)
                ?: throw ObjectNotFoundExcepiton("fail to find user $userId")
            this.candidateId = CandidateEntity.findById(candidateId.value)
                ?: throw ObjectNotFoundExcepiton("fail to find candidate $candidateId")
        }
    }

    override fun countBy(candidateId: CandidateId): NumOfVote =
        NumOfVote(VotingEntity.find { VotingTable.candidateId eq candidateId.value }.count().toLong())


    override fun deleteBy(questionId: QuestionId) =
        VotingEntity.find { VotingTable.questionId eq questionId.value }.single().delete()
}