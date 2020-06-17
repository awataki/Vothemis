package sh.awtk.vothemis.dto

import UserPass
import sh.awtk.vothemis.vo.UserBio
import sh.awtk.vothemis.vo.UserId
import sh.awtk.vothemis.vo.UserName

data class UserDto(
    val id: UserId,
    val name: UserName,
    val password: UserPass,
    val bio: UserBio
)