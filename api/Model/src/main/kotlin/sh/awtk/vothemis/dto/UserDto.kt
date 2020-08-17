package sh.awtk.vothemis.dto

import UserPass
import sh.awtk.vothemis.viewmodel.UserResponse
import sh.awtk.vothemis.vo.UserBio
import sh.awtk.vothemis.vo.UserId
import sh.awtk.vothemis.vo.UserName

data class UserDto(
    val id: UserId,
    val name: UserName = UserName(""),
    var password: UserPass = UserPass(""),
    val bio: UserBio = UserBio("")
)

fun UserDto.toResponse() = UserResponse(
    id = id.value,
    name = name.value,
    bio = bio.value ?: ""
)