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
    if (this.title.value.isBlank()) throw BadRequestException("QuestionDto.title is empty", "タイトルが不正です")
    if (this.sentence.value.isBlank()) throw BadRequestException("QuestionDto.sentence is empty", "質問文が不正です")
    if (this.candidates.isNullOrEmpty()) throw BadRequestException("QuestionDto.candidates is empty", "候補は１つ以上でなければなりません")
    if (this.until < DateTime.now().toDate()) throw BadRequestException("QuestionDto.until out dated", "締切は未来でなければなりません")
    return this
}