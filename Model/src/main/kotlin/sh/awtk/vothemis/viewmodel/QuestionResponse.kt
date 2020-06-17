package sh.awtk.vothemis.viewmodel

import java.util.*

data class QuestionResponse(
    val id: Long,
    val title: String,
    val sentence: String,
    val available_candidate: List<Candidate>,
    val until: Date,
    val created_by: UserResponse
)