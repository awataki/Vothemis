package sh.awtk.vothemis.`interface`

import sh.awtk.vothemis.dto.UserDto
import sh.awtk.vothemis.vo.UserId

interface IUserRepository {
    fun create(user: UserDto): UserDto
    fun findBy(id: Int): UserDto
    fun findAll(): List<UserDto>
    fun update(user: UserDto): UserId
    fun delete(id: Int): UserId
}