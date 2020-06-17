package sh.awtk.vothemis.viewmodel

data class Candidate(
    val candidate_id: Long,
    val question_id: Long,
    val description: String,
    val num_of_vote: String
)