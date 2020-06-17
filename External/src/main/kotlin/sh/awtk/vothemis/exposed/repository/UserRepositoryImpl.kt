package sh.awtk.vothemis.exposed.repository

import sh.awtk.vothemis.dto.UserDto
import sh.awtk.vothemis.exposed.table.UserEntity
import sh.awtk.vothemis.interfaces.repository.IUserRepository
import sh.awtk.vothemis.vo.UserId

class UserRepositoryImpl : IUserRepository {
    override fun create(user: UserDto): UserDto = UserEntity.new {
        name = user.name.value
        hash = user.password.value
        bio = user.bio.value
    }.toDto()


    override fun findBy(id: Long): UserDto? = UserEntity.findById(id)?.toDto()


    override fun findAll(): List<UserDto>? = UserEntity.all().map { it.toDto() }

    override fun update(user: UserDto): UserId? = UserEntity.findById(user.id.value)?.also {
        it.name = user.name.value
        it.bio = user.bio.value
        it.hash = user.password.value
    }?.toUserId()

    override fun delete(id: Long): Unit? = UserEntity.findById(id)?.delete()
}