package sh.awtk.vothemis.presenter

import sh.awtk.vothemis.interfaces.service.IAuthenticationService
import sh.awtk.vothemis.viewmodel.LoginRequest
import sh.awtk.vothemis.viewmodel.TokenPair
import sh.awtk.vothemis.viewmodel.toDto

class LoginPresenter(private val authenticationService: IAuthenticationService) {
    suspend fun login(loginRequest: LoginRequest): TokenPair = authenticationService.login(loginRequest.toDto())
}