package sh.awtk.vothemis.viewmodel

import UserPass
import sh.awtk.vothemis.dto.UserDto
import sh.awtk.vothemis.vo.UserId
import sh.awtk.vothemis.vo.UserName

data class LoginRequest(
    val name: String,
    val password: String
)

fun LoginRequest.toDto(): UserDto {
    return UserDto(
        UserId(0),
        name = UserName(name),
        password = UserPass(password)
    )
}