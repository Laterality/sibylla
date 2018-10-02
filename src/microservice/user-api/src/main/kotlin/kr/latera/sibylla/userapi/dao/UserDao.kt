package kr.latera.sibylla.userapi.dao

import kr.latera.sibylla.userapi.dto.UserDto
import kr.latera.sibylla.userapi.dto.UserInsertDto
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class UserDao(dataSource: DataSource) {

    private val jdbc = NamedParameterJdbcTemplate(dataSource)
    private val insertAction = SimpleJdbcInsert(dataSource)
            .withTableName("user")
            .usingColumns("email", "password", "salt")
            .usingGeneratedKeyColumns("id")

    val userDtoRowMapper = RowMapper { rs, _ ->
        UserDto(rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("salt"),
                rs.getTimestamp("reg_date"),
                rs.getTimestamp("mod_date"))
    }

    fun insert(user: UserInsertDto): Long {
        val params = HashMap<String, Any>()

        params["email"] = user.email
        params["password"] = user.password
        params["salt"] = user.salt
        return insertAction.executeAndReturnKey(params).toLong()
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

    fun selectById(id: Long): UserDto? {
        val params = HashMap<String, Long>()

        params["id"] = id

        return try {
            jdbc.queryForObject(UserDaoSql.SELECT_BY_ID, params, userDtoRowMapper)
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    fun update(user: UserDto): Int {
        val params = HashMap<String, Any>()

        params["id"] = user.id
        params["email"] = user.email
        params["password"] = user.password
        params["salt"] = user.salt

        return jdbc.update(UserDaoSql.UPDATE, params)
    }

    fun deleteUser(id: Long): Int {
        val params = HashMap<String, Long>()
        params["id"] = id
        return jdbc.update(UserDaoSql.DELETE_BY_ID, params)
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
            const val UPDATE =
                    "UPDATE user\n" +
                            "SET\n" +
                            "\temail=:email,\n" +
                            "\tpassword=:password,\n" +
                            "\tsalt=:salt,\n" +
                            "\tmod_date=CURRENT_TIMESTAMP\n" +
                            "WHERE id=:id;"
            const val DELETE_BY_ID =
                    "DELETE FROM user\n" +
                            "WHERE id=:id;"
        }
    }
}