package sh.awtk.vothemis.module

import org.koin.dsl.module
import org.koin.experimental.builder.single
import org.koin.experimental.builder.singleBy
import sh.awtk.vothemis.exposed.repository.TransactionImpl
import sh.awtk.vothemis.exposed.repository.UserRepositoryImpl
import sh.awtk.vothemis.interfaces.repository.ITransaction
import sh.awtk.vothemis.interfaces.repository.IUserRepository
import sh.awtk.vothemis.interfaces.service.IAuthenticationService
import sh.awtk.vothemis.interfaces.service.IUserService
import sh.awtk.vothemis.presenter.LoginPresenter
import sh.awtk.vothemis.presenter.UserPresenter
import sh.awtk.vothemis.service.AuthenticationService
import sh.awtk.vothemis.service.UserService

class KoinModules {
    companion object {
        val modules = module {
            // Presenter
            single<UserPresenter>()
            single<LoginPresenter>()
            // Service
            singleBy<IUserService, UserService>()
            singleBy<IAuthenticationService, AuthenticationService>()
            //Repository
            singleBy<ITransaction, TransactionImpl>()
            singleBy<IUserRepository, UserRepositoryImpl>()
        }
    }
}