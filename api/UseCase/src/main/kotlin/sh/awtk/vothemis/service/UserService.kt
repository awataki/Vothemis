package sh.awtk.vothemis.service

import UserPass
import sh.awtk.vothemis.bcrypt.BCryptFactory
import sh.awtk.vothemis.dto.UserDto
import sh.awtk.vothemis.exception.BadRequestException
import sh.awtk.vothemis.exception.ObjectNotFoundException
import sh.awtk.vothemis.interfaces.repository.ITransaction
import sh.awtk.vothemis.interfaces.repository.IUserRepository
import sh.awtk.vothemis.interfaces.service.IUserService
import sh.awtk.vothemis.vo.UserId

class UserService(
    private val userRepo: IUserRepository,
    private val transaction: ITransaction
) : IUserService {
    override suspend fun createNewUser(user: UserDto): UserDto {
        user.also {
            if (it.name.value.isBlank()) throw BadRequestException("user.name is blank", "ユーザー名が空です")
            if (it.password.value.isBlank()) throw BadRequestException("user.pass is blank", "パスワードが空です")
            it.password = UserPass(BCryptFactory.genBCryptHash(it.password.value))
        }
        return transaction.suspendRun {
            userRepo.create(user)
        }
    }

    override suspend fun getSpecificUser(id: UserId): UserDto {
        return transaction.run {
            userRepo.findBy(id)
        } ?: throw ObjectNotFoundException("find user fail $id","存在しないユーザーです")
    }

    override suspend fun updateUserData(user: UserDto): UserDto {
        return transaction.run {
            user.also {
                if (it.name.value.isBlank()) throw BadRequestException("user.name is blank", "ユーザー名が空です")
                if (it.password.value.isBlank()) it.password =
                    userRepo.findBy(it.id)?.password ?: throw ObjectNotFoundException("find user fail ${it.id.value}","不正なリクエストです")
                else it.password = UserPass(BCryptFactory.genBCryptHash(it.password.value))
            }
            userRepo.update(user)?.value
            userRepo.findBy(user.id)
        } ?: throw ObjectNotFoundException("find user fail ${user.id.value}","ユーザーが見つかりませんでした")
    }

    override suspend fun deleteUser(id: UserId): Unit? {
        return transaction.run {
            userRepo.delete(id.value)
        } ?: throw ObjectNotFoundException("find user fail $id","ユーザーが見つかりませんでした")
    }
}