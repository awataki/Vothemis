package sh.awtk.vothemis.service

import sh.awtk.vothemis.dto.UserDto
import sh.awtk.vothemis.exception.ObjectNotFoundExcepiton
import sh.awtk.vothemis.interfaces.repository.ITransaction
import sh.awtk.vothemis.interfaces.repository.IUserRepository
import sh.awtk.vothemis.interfaces.service.IUserService
import sh.awtk.vothemis.vo.UserId

class UserService(
    private val userRepo: IUserRepository,
    private val transaction: ITransaction
) : IUserService {
    override suspend fun createNewUser(user: UserDto): UserDto = transaction.suspendRun {
        userRepo.create(user)

    }

    override suspend fun getSpecificUser(id: UserId): UserDto = transaction.run {
        userRepo.findBy(id.value)
    } ?: throw ObjectNotFoundExcepiton("find user fail $id")

    override suspend fun updateUserData(user: UserDto): UserId = transaction.run {
        userRepo.update(user)
    } ?: throw ObjectNotFoundExcepiton("find user fail ${user.id.value}")

    override suspend fun deleteUser(id: UserId): Unit? = transaction.run {
        userRepo.delete(id.value)
    } ?: throw ObjectNotFoundExcepiton("find user fail $id")
}