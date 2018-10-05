package kr.latera.sibylla.authapi.dao

import kr.latera.sibylla.authapi.dto.AuthTokenDto
import kr.latera.sibylla.authapi.dto.AuthTokenInsertDto
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import javax.sql.DataSource

class AuthTokenDao(dataSource: DataSource) {

    private val jdbc = NamedParameterJdbcTemplate(dataSource)
    private val insertAction = SimpleJdbcInsert(dataSource)
            .withTableName("auth_token")
            .usingGeneratedKeyColumns("id")
            .usingColumns("token", "user_id")

    private val authTokenDtoRowMapper = RowMapper {
        rs, _ -> AuthTokenDto(
            rs.getLong("id"),
            rs.getLong("user_id"),
            rs.getString("token"),
            rs.getTimestamp("reg_date"),
            rs.getTimestamp("mod_date"))
    }

    fun insert(token: AuthTokenInsertDto): Long {
        val params = HashMap<String, Any>()

        params["user_id"] = token.userId
        params["token"] = token.token

        return insertAction.executeAndReturnKey(params).toLong()
    }

    fun selectByUserId(userId: Long): AuthTokenDto? {
        val params = HashMap<String, Long>()

        params["userId"] = userId

        return try {
            jdbc.queryForObject(AuthTokenDaoSql.SELECT_BY_USER_ID, params, authTokenDtoRowMapper)
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    fun selectByToken(token: String): AuthTokenDto? {
        val params = HashMap<String, String>()

        params["token"] = token

        return try {
            jdbc.queryForObject(AuthTokenDaoSql.SELECT_BY_TOKEN, params, authTokenDtoRowMapper)
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    fun selectById(id: Long): AuthTokenDto? {
        val params = HashMap<String, Long>()

        params["id"] = id

        return try {
            jdbc.queryForObject(AuthTokenDaoSql.SELECT_BY_ID, params, authTokenDtoRowMapper)
        } catch(e: EmptyResultDataAccessException) {
            null
        }
    }

    fun update(token: AuthTokenDto): Int {
        val params = HashMap<String, Any>()

        params["id"] = token.id
        params["token"] = token.token

        return jdbc.update(AuthTokenDaoSql.UPDATE, params)
    }

    fun delete(id: Long): Int {
        val params = HashMap<String, Long>()

        params["id"] = id

        return jdbc.update(AuthTokenDaoSql.DELETE_BY_ID, params)
    }

    class AuthTokenDaoSql {
        companion object {
            const val SELECT_BY_USER_ID =
                    "SELECT id, token, user_id, reg_date, mod_date\n" +
                            "FROM auth_token\n" +
                            "WHERE user_id=:userId;"
            const val SELECT_BY_TOKEN =
                    "SELECT id, token, user_id, reg_date, mod_date\n" +
                            "FROM auth_token\n" +
                            "WHERE token=:token;"
            const val SELECT_BY_ID =
                    "SELECT id, token, user_id\n" +
                            "FROM auth_token\n" +
                            "WHERE id=:id;"
            const val UPDATE =
                    "UPDATE auth_token\n" +
                            "SET\n" +
                            "\ttoken=:token,\n" +
                            "\tmod_date=CURRENT_TIMESTAMP\n" +
                            "WHERE id=:id;"
            const val DELETE_BY_ID =
                    "DELETE FROM auth_token\n" +
                            "WHERE id=:id;"
        }
    }
}