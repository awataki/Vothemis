package sh.awtk.vothemis.dto

import sh.awtk.vothemis.viewmodel.QuestionResponse
import sh.awtk.vothemis.vo.QuestionId
import sh.awtk.vothemis.vo.QuestionSentence
import sh.awtk.vothemis.vo.QuestionTitle
import java.util.*

data class QuestionDto(
    val id: QuestionId,
    val title: QuestionTitle,
    val sentence: QuestionSentence,
    val candidates: List<CandidateDto>,
    val until: Date,
    val createdBy: UserDto
)

private fun QuestionDto.toResponse(): QuestionResponse = QuestionResponse(
    id.value,
    title.value,
    sentence.value,
    candidates.map { it.toViewModel() },
    until,
    createdBy.toResponse()
)