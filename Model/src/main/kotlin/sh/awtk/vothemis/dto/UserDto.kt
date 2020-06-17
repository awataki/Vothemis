package sh.awtk.vothemis.dto

import UserPass
import sh.awtk.vothemis.viewmodel.UserResponse
import sh.awtk.vothemis.vo.UserBio
import sh.awtk.vothemis.vo.UserId
import sh.awtk.vothemis.vo.UserName

data class UserDto(
    val id: UserId,
    val name: UserName,
    var password: UserPass,
    val bio: UserBio
)

fun UserDto.toResponse() = UserResponse(
    id = id.value,
    name = name.value,
    bio = bio.value ?: ""
)