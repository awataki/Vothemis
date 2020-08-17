package sh.awtk.vothemis.interfaces.repository

import sh.awtk.vothemis.dto.UserDto
import sh.awtk.vothemis.vo.UserId
import sh.awtk.vothemis.vo.UserName

interface IUserRepository {
    fun create(user: UserDto): UserDto
    fun findBy(id: UserId): UserDto?
    fun findBy(name: UserName): UserDto?
    fun findAll(): List<UserDto>?
    fun update(user: UserDto): UserId?
    fun delete(id: Long): Unit?
}