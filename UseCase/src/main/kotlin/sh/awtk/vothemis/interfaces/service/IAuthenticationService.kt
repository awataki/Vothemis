package sh.awtk.vothemis.interfaces.service

import sh.awtk.vothemis.dto.UserDto
import sh.awtk.vothemis.viewmodel.TokenPair

interface IAuthenticationService {
    fun login(user: UserDto): TokenPair
}