package kr.latera.sibylla.articleapi.service

import kr.latera.sibylla.articleapi.dao.AuthTokenDao
import kr.latera.sibylla.articleapi.dto.AuthTokenDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class AuthTokenServiceImpl : AuthTokenService {

    @Autowired
    private lateinit var dataSource: DataSource

    override fun getByToken(token: String): AuthTokenDto? {
        return AuthTokenDao(dataSource).selectByToken(token)
    }

}