package sh.awtk.vothemis

import com.auth0.jwt.algorithms.Algorithm
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
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import org.koin.ktor.ext.Koin
import sh.awtk.vothemis.exception.HttpException
import sh.awtk.vothemis.exposed.DatabaseFactory
import sh.awtk.vothemis.jwt.JWTFactory
import sh.awtk.vothemis.module.UserModule
import sh.awtk.vothemis.principal.LoginUser
import sh.awtk.vothemis.routes.v1Route

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    setupDB()
    setupJWT()

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
private fun Application.setupDB() {
    DatabaseFactory.also {
        it.dbUrl = requireNotNull(environment.config.property("vothemis.db.jdbcUrl").getString())
        it.dbUser = requireNotNull(environment.config.property("vothemis.db.username").getString())
        it.dbPassword = requireNotNull(environment.config.property("vothemis.db.password").getString())
        it.dbDriver = requireNotNull(environment.config.property("vothemis.db.driverClassName").getString())
        it.maxPoolSize = requireNotNull(environment.config.property("vothemis.db.maximumPoolSize").getString().toInt())
        it.isAutoCommit =
            requireNotNull(environment.config.property("vothemis.db.isAutoCommit").getString().toBoolean())
        it.transactionIsolation =
            requireNotNull(environment.config.property("vothemis.db.transactionIsolation").getString())
    }
    DatabaseFactory.init()
}

@KtorExperimentalAPI
private fun Application.setupJWT() {
    JWTFactory.also {
        it.algorithm = Algorithm.HMAC512(requireNotNull(environment.config.property("vothemis.jwt.secret").getString()))
        it.issuer = requireNotNull(environment.config.property("vothemis.jwt.issuer").getString())
        it.audience = requireNotNull(environment.config.property("vothemis.jwt.audience").getString())
    }

    JWTFactory.init()
}

@KtorExperimentalAPI
private fun Application.installAuthentication() {
    val jwtRealm = requireNotNull(environment.config.property("vothemis.jwt.realm").getString())

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

private fun Application.installKoin() {
    install(Koin) {
        modules(
            UserModule().module()
        )
    }
}