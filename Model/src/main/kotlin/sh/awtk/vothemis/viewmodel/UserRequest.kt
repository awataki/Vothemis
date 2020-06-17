package sh.awtk.vothemis.viewmodel

import UserPass
import sh.awtk.vothemis.dto.UserDto
import sh.awtk.vothemis.vo.UserBio
import sh.awtk.vothemis.vo.UserId
import sh.awtk.vothemis.vo.UserName

data class UserRequest(
    val name: String,
    val password: String,
    val bio: String
)

fun UserRequest.toDto(id: Long = 0): UserDto = UserDto(
    id = UserId(id),
    name = UserName(name),
    password = UserPass(password),
    bio = UserBio(bio)
)