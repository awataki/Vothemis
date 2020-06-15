package sh.awtk.vothemis.routes

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import sh.awtk.vothemis.viewmodel.Question
import sh.awtk.vothemis.viewmodel.User
import sh.awtk.vothemis.viewmodel.Voting


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
private fun Route.questionRoute() {
    @Location("/question/{id}")
    data class SpecificQuestionLocation(val id: Long)

    // Create new question
    post("/question") {
        val body = call.receive<Question>()
        call.respond(Unit)
    }

    // Get specific question by ID
    get<SpecificQuestionLocation> { param ->
        call.respond(Unit)
    }

    // Update specific question by ID
    put<SpecificQuestionLocation> {
        val body = call.receive<Question>()
        call.respond(Unit)
    }

    // Delete specific question by ID
    delete<SpecificQuestionLocation> {
        call.respond(Unit)
    }

    // Voting to question
    post<SpecificQuestionLocation> {
        val body = call.receive<Voting>()
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
private fun Route.userRoute() {
    @Location("/user/{id}")
    data class SpecificUserLocation(val id: Long)

    // Create new User
    post("/user/create") {
        val body = call.receive<User>()
        call.respond(Unit)
    }

    // Get user data by ID
    get<SpecificUserLocation> {
        call.respond(Unit)
    }

    // Update own user data
    put<SpecificUserLocation> {
        val body = call.receive<User>()
        call.respond(Unit)
    }

}

/**
 * Login
 */
private fun Route.loginRoute() {

    // Login
    post("/login") {
        val body = call.receive<User>()
        call.respond(Unit)
    }
}

