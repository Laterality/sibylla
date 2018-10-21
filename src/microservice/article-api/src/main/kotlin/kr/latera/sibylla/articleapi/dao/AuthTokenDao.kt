package kr.latera.sibylla.articleapi.dao

import kr.latera.sibylla.articleapi.dto.AuthTokenDto
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class AuthTokenDao(dataSource: DataSource) {

    private val jdbc = NamedParameterJdbcTemplate(dataSource)
    private val authTokenRowMapper = RowMapper {
        rs, _ -> AuthTokenDto(
            rs.getLong("id"),
            rs.getLong("user_id"),
            rs.getString("token"),
            rs.getTimestamp("reg_date"),
            rs.getTimestamp("mod_date"))
    }

    fun selectByToken(token: String): AuthTokenDto? {
        val params = HashMap<String, String>()
        params["token"] = token

        return try {
            jdbc.queryForObject(AuthTokenDaoSql.SELECT_BY_TOKEN, params, authTokenRowMapper)
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    class AuthTokenDaoSql {
        companion object {
            const val SELECT_BY_TOKEN =
                    "SELECT id, user_id, token, reg_date, mod_date\n" +
                            "FROM auth_token\n" +
                            "WHERE token=:token;"
        }
    }
}