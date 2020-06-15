package sh.awtk.vothemis.viewmodel

import java.util.*

data class Question(
    val id: Long,
    val title: String,
    val sentence: String,
    val avaliable_candidate: List<Candidate>,
    val until: Date,
    val created_by: User
)
