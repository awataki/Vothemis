package sh.awtk.vothemis.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.util.*
import kotlin.properties.Delegates

object JWTFactory {
    var algorithm by Delegates.notNull<Algorithm>()
    var issuer by Delegates.notNull<String>()
    var audience by Delegates.notNull<String>()
    var verifyer by Delegates.notNull<JWTVerifier>()

    fun init(secret: String, iss: String, audi: String) {
        this.algorithm = Algorithm.HMAC512(secret)
        this.issuer = iss
        this.audience = audi
        this.verifyer = JWT.require(algorithm).withAudience(audience).withIssuer(issuer).build()
    }

    fun newToken(userId: Long): String {
        return JWT.create().withIssuer(issuer).withAudience(audience).withClaim("user_id", userId).withExpiresAt(calcExpires()).sign(algorithm)
    }

    private fun calcExpires(): Date = DateTime.now(DateTimeZone.UTC).plusDays(7).toDate()
}