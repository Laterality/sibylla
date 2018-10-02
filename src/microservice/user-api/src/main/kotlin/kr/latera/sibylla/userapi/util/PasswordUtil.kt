package kr.latera.sibylla.userapi.util

import org.springframework.security.crypto.bcrypt.BCrypt

class PasswordUtil {
    companion object {
        private val LOG_ROUND = 10

        /**
         * Generate new salt string
         *
         * @return generated salt
         */
        fun makeSalt(): String {
            return BCrypt.gensalt(LOG_ROUND)
        }

        /**
         * Encrypt with plain text and salt
         *
         * @param password password to Hash
         * @param salt salt string to use for hash
         * @return hashed string
         */
        fun encrypt(password: String, salt: String): String {
            return BCrypt.hashpw(password, salt)
        }

        /**
         * Check password matches with hash
         *
         * @param hashed password hash
         * @param passwordToCheck plain text to check
         * @return true if password match, false otherwise
         */
        fun check(hashed: String, passwordToCheck: String): Boolean {
            return BCrypt.checkpw(passwordToCheck, hashed)
        }
    }
}