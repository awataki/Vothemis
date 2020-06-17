package sh.awtk.vothemis.viewmodel

import java.util.*

data class QuestionRequest(
    val id: Long,
    val title: String,
    val sentence: String,
    val available_candidate: List<Candidate>,
    val until: Date
)
