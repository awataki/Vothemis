package sh.awtk.vothemis.viewmodel

import sh.awtk.vothemis.dto.CandidateDto
import sh.awtk.vothemis.vo.CandidateDescription
import sh.awtk.vothemis.vo.CandidateId
import sh.awtk.vothemis.vo.NumOfVote
import sh.awtk.vothemis.vo.QuestionId

data class Candidate(
    val candidate_id: Long,
    val question_id: Long,
    val description: String,
    val num_of_vote: Long
)

fun Candidate.toDto(): CandidateDto = CandidateDto(
    id = CandidateId(candidate_id),
    questionId = QuestionId(question_id),
    description = CandidateDescription(description),
    numOfVote = NumOfVote(num_of_vote)
)