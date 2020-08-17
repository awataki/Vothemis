package sh.awtk.vothemis.viewmodel

import sh.awtk.vothemis.dto.QuestionDto
import sh.awtk.vothemis.vo.QuestionId
import sh.awtk.vothemis.vo.QuestionSentence
import sh.awtk.vothemis.vo.QuestionTitle
import java.util.*

data class QuestionRequest(
    val id: Long,
    val title: String,
    val sentence: String,
    val available_candidate: List<Candidate>,
    val until: Date
)

fun QuestionRequest.toDto(): QuestionDto = QuestionDto(
    id = QuestionId(id),
    title = QuestionTitle(title),
    sentence = QuestionSentence(sentence),
    candidates = available_candidate.map { it.toDto() },
    until = until,
    createdBy = null
)