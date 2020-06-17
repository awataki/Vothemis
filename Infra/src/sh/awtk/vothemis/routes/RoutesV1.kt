package sh.awtk.vothemis.routes

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.principal
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import sh.awtk.vothemis.exception.ForbiddenException
import sh.awtk.vothemis.presenter.UserPresenter
import sh.awtk.vothemis.principal.LoginUser
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
    @Location("/question/{id}")
    data class SpecificQuestionLocation(val id: Long)

    // Create new question
    post("/question") {
        val body = call.receive<QuestionRequest>()
        call.respond(Unit)
    }

    // Get specific question by ID
    get<SpecificQuestionLocation> { param ->
        call.respond(Unit)
    }

    // Update specific question by ID
    put<SpecificQuestionLocation> {
        val body = call.receive<QuestionRequest>()
        call.respond(Unit)
    }

    // Delete specific question by ID
    delete<SpecificQuestionLocation> {
        call.respond(Unit)
    }

    // Voting to question
    post<SpecificQuestionLocation> {
        val body = call.receive<VotingRequest>()
        call.respond(Unit)
    }

    // Get all question list
    get("/question/list") {
        call.respond(Unit)
    }
}

/**
 * User
 */
@KtorExperimentalLocationsAPI
private fun Route.userRoute() {
    val presenter: UserPresenter by inject()

    @Location("/user/{id}")
    data class SpecificUserLocation(val id: Long)

    // Create new User
    post("/user/create") {
        val body = call.receive<UserRequest>()
        call.respond(presenter.registUser(body))
    }

    // Get user data by ID
    get<SpecificUserLocation> { param ->
        call.respond(presenter.getUser(param.id))
    }

    // Update own user data
    put<SpecificUserLocation> { param ->
        val body = call.receive<UserRequest>()
        val tokenId = call.principal<LoginUser>()?.id
        if (tokenId != param.id) throw ForbiddenException("invalid user request")

        call.respond(presenter.updateUserData(param.id, body))
    }

}

/**
 * Login
 */
private fun Route.loginRoute() {

    // Login
    post("/login") {
        val body = call.receive<UserRequest>()
        call.respond(Unit)
    }
}

