package sh.awtk.vothemis.exposed.repository

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import sh.awtk.vothemis.dto.VotingDto
import sh.awtk.vothemis.exception.ObjectNotFoundException
import sh.awtk.vothemis.exposed.table.*
import sh.awtk.vothemis.interfaces.repository.IVotingRepository
import sh.awtk.vothemis.vo.CandidateId
import sh.awtk.vothemis.vo.NumOfVote
import sh.awtk.vothemis.vo.QuestionId

class VotingRepositoryImpl : IVotingRepository {
    override fun replace(votingDto: VotingDto) {
        VotingEntity.find {
            (VotingTable.questionId eq votingDto.questionId.value) and
                    (VotingTable.userId eq votingDto.userId.value)
        }.let {
            if (!it.empty()) it.single().delete()
        }
        VotingEntity.new {
            this.questionId = QuestionEntity.findById(votingDto.questionId.value)
                ?: throw  ObjectNotFoundException("fail to find question $questionId")
            this.userId = UserEntity.findById(votingDto.userId.value)
                ?: throw ObjectNotFoundException("fail to find user $userId")
            this.candidateId = CandidateEntity.findById(votingDto.candidateId.value)
                ?: throw ObjectNotFoundException("fail to find candidate $candidateId")
        }
    }

    override fun findBy(questionId: QuestionId): List<VotingDto> {
        return VotingEntity.find { VotingTable.questionId eq questionId.value }.map { it.toDto() }
    }

    override fun countBy(candidateId: CandidateId): NumOfVote =
        NumOfVote(VotingEntity.find { VotingTable.candidateId eq candidateId.value }.count().toLong())


    override fun deleteBy(questionId: QuestionId) {
        VotingTable.deleteWhere { VotingTable.questionId eq questionId.value }
    }
}