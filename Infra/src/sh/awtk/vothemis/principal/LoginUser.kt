package sh.awtk.vothemis.principal

import io.ktor.auth.Principal
import java.util.*

data class LoginUser(val id: Long, val expired: Date) : Principal