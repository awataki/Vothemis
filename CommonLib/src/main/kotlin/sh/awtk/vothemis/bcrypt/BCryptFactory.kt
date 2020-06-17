package sh.awtk.vothemis.bcrypt

import org.springframework.security.crypto.bcrypt.BCrypt

object BCryptFactory {
    fun genBCryptHash(plain: String): String {
        val salt: String = BCrypt.gensalt()
        return BCrypt.hashpw(plain, salt)
    }

    fun checkBCrypt(plain: String, hash: String): Boolean {
        return BCrypt.checkpw(plain, hash)
    }
}