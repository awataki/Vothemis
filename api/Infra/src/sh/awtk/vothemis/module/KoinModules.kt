package sh.awtk.vothemis.module

import org.koin.dsl.module
import org.koin.experimental.builder.single
import org.koin.experimental.builder.singleBy
import sh.awtk.vothemis.exposed.repository.*
import sh.awtk.vothemis.interfaces.repository.*
import sh.awtk.vothemis.interfaces.service.IAuthenticationService
import sh.awtk.vothemis.interfaces.service.IQuestionService
import sh.awtk.vothemis.interfaces.service.IUserService
import sh.awtk.vothemis.presenter.LoginPresenter
import sh.awtk.vothemis.presenter.QuestionPresenter
import sh.awtk.vothemis.presenter.UserPresenter
import sh.awtk.vothemis.service.AuthenticationService
import sh.awtk.vothemis.service.QuestionService
import sh.awtk.vothemis.service.UserService

class KoinModules {
    companion object {
        val modules = module {
            // Presenter
            single<UserPresenter>()
            single<LoginPresenter>()
            single<QuestionPresenter>()
            // Service
            singleBy<IUserService, UserService>()
            singleBy<IAuthenticationService, AuthenticationService>()
            singleBy<IQuestionService, QuestionService>()
            //Repository
            singleBy<ITransaction, TransactionImpl>()
            singleBy<IUserRepository, UserRepositoryImpl>()
            singleBy<IQuestionRepository, QuestionRepositoryImpl>()
            singleBy<ICandidateRepository, CandidateRepositoryImpl>()
            singleBy<IVotingRepository, VotingRepositoryImpl>()
        }
    }
}