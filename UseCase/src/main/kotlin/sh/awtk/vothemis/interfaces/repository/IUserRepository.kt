package sh.awtk.vothemis.interfaces.repository

import sh.awtk.vothemis.dto.UserDto
import sh.awtk.vothemis.vo.UserId

interface IUserRepository {
    fun create(user: UserDto): UserDto
    fun findBy(id: Long): UserDto?
    fun findAll(): List<UserDto>?
    fun update(user: UserDto): UserId?
    fun delete(id: Long): UserId?
}