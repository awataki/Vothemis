package sh.awtk.vothemis.interfaces.service

import sh.awtk.vothemis.dto.UserDto
import sh.awtk.vothemis.vo.UserId

interface IUserService {
    suspend fun createNewUser(user: UserDto): UserDto
    suspend fun getSpecificUser(id: UserId): UserDto
    suspend fun updateUserData(user: UserDto): UserId
    suspend fun deleteUser(id: UserId): Unit?
}