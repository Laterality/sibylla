package kr.latera.sibylla.authapi.service

import kr.latera.sibylla.authapi.dao.AuthTokenDao
import kr.latera.sibylla.authapi.dao.UserDao
import kr.latera.sibylla.authapi.dto.AuthTokenDto
import kr.latera.sibylla.authapi.dto.AuthTokenInsertDto
import kr.latera.sibylla.authapi.util.AuthUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.sql.DataSource

@Service
@EnableTransactionManagement
class AuthServiceImpl : AuthService {

    @Autowired
    private lateinit var dataSource: DataSource

    @Value("\${private-key-path}")
    private lateinit var privKeyPath: String

    @Value("\${jwt-issuer}")
    private lateinit var issuer: String

    /**
     * Check if email and password match and generate token
     *
     * @param email user email
     * @param password plain text of user password input
     *
     * @return Generated token if matched, otherwise null
     */
    @Transactional(readOnly=false)
    override fun login(email: String, password: String, from: String): String? {
        val user = UserDao(dataSource).selectByEmail(email) ?: return null

        val match = BCrypt.checkpw(password, user.password)
        val dao = AuthTokenDao(dataSource)
        var tokenId = dao.selectByUserId(user.id)?.id ?: dao.insert(AuthTokenInsertDto(user.id, "INVALID"))
        val token = AuthUtil.generateToken(privKeyPath, issuer, "user/" + user.id, from, tokenId.toString())
        val now = Date()
        dao.update(AuthTokenDto(tokenId, user.id, token, now, now))

        return if (match) {
            token
        } else {
            null
        }
    }

    /**
     * Remove token
     */
    @Transactional(readOnly=false)
    override fun logout(token: String): Boolean {
        val dao = AuthTokenDao(dataSource)
        val selectedToken = dao.selectByToken(token) ?: return false
        val affected = dao.delete(selectedToken.id)
        return affected == 1
    }
}