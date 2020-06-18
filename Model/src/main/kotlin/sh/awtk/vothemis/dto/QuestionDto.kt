package sh.awtk.vothemis.dto

import org.joda.time.DateTime
import sh.awtk.vothemis.exception.BadRequestException
import sh.awtk.vothemis.viewmodel.QuestionResponse
import sh.awtk.vothemis.vo.QuestionId
import sh.awtk.vothemis.vo.QuestionSentence
import sh.awtk.vothemis.vo.QuestionTitle
import java.util.*

data class QuestionDto(
    val id: QuestionId,
    val title: QuestionTitle,
    val sentence: QuestionSentence,
    var candidates: List<CandidateDto>,
    val until: Date,
    var createdBy: UserDto?
)

fun QuestionDto.toResponse(): QuestionResponse = QuestionResponse(
    id.value,
    title.value,
    sentence.value,
    candidates.map { it.toViewModel() },
    until,
    createdBy!!.toResponse()
)

fun QuestionDto.validate(): QuestionDto {
    if (this.title.value.isBlank()) throw BadRequestException("")
    if (this.sentence.value.isBlank()) throw BadRequestException("")
    if (this.candidates.isNullOrEmpty()) throw BadRequestException("")
    if (this.until < DateTime.now().toDate()) throw BadRequestException("")
    return this
}