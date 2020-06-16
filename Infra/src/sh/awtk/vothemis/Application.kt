package sh.awtk.vothemis

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.locations.Locations
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import org.koin.ktor.ext.Koin
import sh.awtk.vothemis.exception.HttpException
import sh.awtk.vothemis.jwt.JWTFactory
import sh.awtk.vothemis.principal.LoginUser
import sh.awtk.vothemis.routes.v1Route

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(Locations)
    installAuthentication()
    installContentNegotiation()
    installStatusPages()
    installCORS()
    installKoin()

    routing {
        v1Route()
    }
}

@KtorExperimentalAPI
private fun Application.installAuthentication() {
    val jwtSecret = requireNotNull(environment.config.property("vothemis.jwt.secret").getString())
    val jwtIssuer = requireNotNull(environment.config.property("vothemis.jwt.issuer").getString())
    val jwtAudience = requireNotNull(environment.config.property("vothemis.jwt.audience").getString())
    val jwtRealm = requireNotNull(environment.config.property("vothemis.jwt.realm").getString())
    JWTFactory.init(jwtSecret, jwtIssuer, jwtAudience)

    install(Authentication) {
        jwt {
            realm = jwtRealm
            verifier(JWTFactory.verifyer)
            validate {
                val userId = it.payload.getClaim("user_id").asLong()
                val expiredAt = it.payload.expiresAt
                LoginUser(userId, expiredAt)
            }
        }
    }
}

private fun Application.installContentNegotiation() {
    install(ContentNegotiation) {
        gson()
    }
}

private fun Application.installStatusPages() {
    install(StatusPages) {
        exception<HttpException> { e ->
            log.info(e.errMessage)
            call.respond(HttpStatusCode.fromValue(e.code))
        }
        exception<Throwable> { e ->
            log.info(e.toString())
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}

private fun Application.installCORS() {
    install(CORS) {
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Options)
        anyHost()
        header("Authorization")
        allowCredentials = false
    }
}

private fun Application.installKoin(){
    install(Koin){
    }
}