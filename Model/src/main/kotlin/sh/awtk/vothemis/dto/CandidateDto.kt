package sh.awtk.vothemis.dto

import sh.awtk.vothemis.viewmodel.Candidate
import sh.awtk.vothemis.vo.CandidateDescription
import sh.awtk.vothemis.vo.CandidateId
import sh.awtk.vothemis.vo.NumOfVote
import sh.awtk.vothemis.vo.QuestionId

data class CandidateDto(
    val id: CandidateId,
    val questionId: QuestionId,
    val description: CandidateDescription,
    val numOfVote: NumOfVote
)

fun CandidateDto.toViewModel() = Candidate(
    id.value,
    questionId.value,
    description.value,
    numOfVote.value
)