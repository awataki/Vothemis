package sh.awtk.vothemis.routes

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.principal
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import sh.awtk.vothemis.exception.AuthenticationException
import sh.awtk.vothemis.exception.ForbiddenException
import sh.awtk.vothemis.presenter.LoginPresenter
import sh.awtk.vothemis.presenter.QuestionPresenter
import sh.awtk.vothemis.presenter.UserPresenter
import sh.awtk.vothemis.principal.LoginUser
import sh.awtk.vothemis.viewmodel.LoginRequest
import sh.awtk.vothemis.viewmodel.QuestionRequest
import sh.awtk.vothemis.viewmodel.UserRequest
import sh.awtk.vothemis.viewmodel.VotingRequest


@KtorExperimentalLocationsAPI
fun Routing.v1Route() {
    route("v1") {
        loginRoute()
        authenticate {
            questionRoute()
            userRoute()
        }
    }
}

/**
 * Question
 */
@KtorExperimentalLocationsAPI
private fun Route.questionRoute() {
    val questionPresenter: QuestionPresenter by inject()

    @Location("/question/{id}")
    data class SpecificQuestionLocation(val id: Long)

    // Create new question
    post("/question") {
        val body = call.receive<QuestionRequest>()
        val tokenId = call.principal<LoginUser>()?.id ?: throw AuthenticationException("invalid token")
        call.respond(questionPresenter.createNewQuestion(body, tokenId))
    }

    // Get specific question by ID
    get<SpecificQuestionLocation> { param ->
        call.respond(questionPresenter.getQuestionById(param.id))
    }

    // Update specific question by ID
    put<SpecificQuestionLocation> {
        val body = call.receive<QuestionRequest>()
        val tokenId = call.principal<LoginUser>()?.id ?: throw AuthenticationException("invalid token")
        call.respond(questionPresenter.updateSpecificQuestion(body, tokenId))
    }

    // Delete specific question by ID
    delete<SpecificQuestionLocation> { param ->
        val tokenId = call.principal<LoginUser>()?.id ?: throw AuthenticationException("invalid token")
        call.respond(questionPresenter.deleteSpecificQuestion(param.id, tokenId))
    }

    // Voting to question
    post<SpecificQuestionLocation> { param ->
        val body = call.receive<VotingRequest>()
        val tokenId = call.principal<LoginUser>()?.id ?: throw AuthenticationException("invalid token")
        call.respond(questionPresenter.votingQuestion(param.id, tokenId, body.candidate_id))
    }

    // Get all question list
    get("/question/list") {
        call.respond(questionPresenter.getAllQuestionList())
    }
}

/**
 * User
 */
@KtorExperimentalLocationsAPI
private fun Route.userRoute() {
    val userPresenter: UserPresenter by inject()

    @Location("/user/{id}")
    data class SpecificUserLocation(val id: Long)

    // Create new User
    post("/user/create") {
        val body = call.receive<UserRequest>()
        call.respond(userPresenter.registUser(body))
    }

    // Get user data by ID
    get<SpecificUserLocation> { param ->
        call.respond(userPresenter.getUser(param.id))
    }

    // Update own user data
    put<SpecificUserLocation> { param ->
        val body = call.receive<UserRequest>()
        val tokenId = call.principal<LoginUser>()?.id
        if (tokenId != param.id) throw ForbiddenException("invalid user request")

        call.respond(userPresenter.updateUserData(param.id, body))
    }

}

/**
 * Login
 */
private fun Route.loginRoute() {
    val loginPresenter: LoginPresenter by inject()

    // Login
    post("/login") {
        val body = call.receive<LoginRequest>()
        call.respond(loginPresenter.login(body))
    }
}

