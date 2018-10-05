package kr.latera.sibylla.authapi.dao

import kr.latera.sibylla.authapi.dto.UserDto
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class UserDao(dataSource: DataSource) {

    private val jdbc = NamedParameterJdbcTemplate(dataSource)
    private val userDtoRowMapper = RowMapper {
        rs, _ -> UserDto(
            rs.getLong("id"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("salt"),
            rs.getTimestamp("reg_date"),
            rs.getTimestamp("mod_date"))
    }


    fun selectById(id: Long): UserDto? {
        val params = HashMap<String, Long>()

        params["id"] = id

        return try {
            jdbc.queryForObject(UserDaoSql.SELECT_BY_ID, params, userDtoRowMapper)
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    fun selectByEmail(email: String): UserDto? {
        val params = HashMap<String, String>()

        params["email"] = email

        return try {
            jdbc.queryForObject(UserDaoSql.SELECT_BY_EMAIL, params, userDtoRowMapper)
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }


    class UserDaoSql {
        companion object {
            const val SELECT_BY_EMAIL =
                    "SELECT id, email, password, salt, reg_date, mod_date\n" +
                            "FROM user\n" +
                            "WHERE email=:email;"
            const val SELECT_BY_ID =
                    "SELECT id, email, password, salt, reg_date, mod_date\n" +
                            "FROM user\n" +
                            "WHERE id=:id;"
        }
    }
}