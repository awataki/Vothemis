package sh.awtk.vothemis.presenter

import sh.awtk.vothemis.dto.toResponse
import sh.awtk.vothemis.interfaces.service.IUserService
import sh.awtk.vothemis.viewmodel.UserRequest
import sh.awtk.vothemis.viewmodel.UserResponse
import sh.awtk.vothemis.viewmodel.toDto
import sh.awtk.vothemis.vo.UserId

class UserPresenter(private val userService: IUserService) {
    suspend fun registUser(userRequest: UserRequest): UserResponse {
        return userService.createNewUser(userRequest.toDto()).toResponse()
    }

    suspend fun getUser(id: Long): UserResponse {
        return userService.getSpecificUser(UserId(id)).toResponse()
    }

    suspend fun updateUserData(id: Long, UserRequest: UserRequest): UserResponse {
        return userService.updateUserData(UserRequest.toDto(id)).toResponse()
    }
}