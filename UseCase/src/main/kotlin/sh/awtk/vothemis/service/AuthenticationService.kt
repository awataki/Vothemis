package sh.awtk.vothemis.service

import sh.awtk.vothemis.bcrypt.BCryptFactory
import sh.awtk.vothemis.dto.UserDto
import sh.awtk.vothemis.exception.AuthenticationException
import sh.awtk.vothemis.exception.ObjectNotFoundExcepiton
import sh.awtk.vothemis.interfaces.repository.ITransaction
import sh.awtk.vothemis.interfaces.repository.IUserRepository
import sh.awtk.vothemis.interfaces.service.IAuthenticationService
import sh.awtk.vothemis.jwt.JWTFactory
import sh.awtk.vothemis.viewmodel.TokenPair

class AuthenticationService(
    private val userRepo: IUserRepository,
    private val transaction: ITransaction
) : IAuthenticationService {
    override suspend fun login(user: UserDto): TokenPair {
        val loginUser = transaction.run {
            userRepo.findBy(user.name) ?: throw ObjectNotFoundExcepiton("fail to find user ${user.id.value}")
        }

        if (!BCryptFactory.checkBCrypt(
                user.password.value,
                loginUser.password.value
            )
        ) throw AuthenticationException("password not match")

        return TokenPair(
            access_token = JWTFactory.newToken(loginUser.id.value),
            // TODO Implement refresh token
            refresh_token = "unimplemented"
        )
    }
}