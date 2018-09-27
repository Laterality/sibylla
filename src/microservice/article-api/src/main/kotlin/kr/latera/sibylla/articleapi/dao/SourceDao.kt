package kr.latera.sibylla.articleapi.dao

import kr.latera.sibylla.articleapi.dto.SourceDto
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import javax.sql.DataSource

class SourceDao(dataSource: DataSource) {

    private val jdbc = NamedParameterJdbcTemplate(dataSource)
    private val insertAction = SimpleJdbcInsert(dataSource)
            .withTableName("source")
            .usingGeneratedKeyColumns("id")
            .usingColumns("name")

    fun insert(name: String): Long {
        val params = HashMap<String, String>()
        params["name"] = name
        return insertAction.executeAndReturnKey(params).toLong()
    }

    fun selectById(id: Long): SourceDto? {
        val params = HashMap<String, Long>()
        params["sourceId"] = id

        return jdbc.queryForObject(SourceDaoSql.SQL_SELECT_BY_ID, params) {
            rs, _ -> SourceDto(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getTimestamp("reg_date"),
                rs.getTimestamp("mod_date"))
        }
    }

    fun selectByName(name: String): SourceDto? {
        val params = HashMap<String, String>()
        params["name"] = name

        try {
            return jdbc.queryForObject(SourceDaoSql.SQL_SELECT_BY_NAME, params) {
                rs, _ -> SourceDto(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getTimestamp("reg_date"),
                    rs.getTimestamp("mod_date"))
            }
        } catch (e: EmptyResultDataAccessException) {
            return null
        }
    }

    fun deleteById(id: Long): Int {
        val params = HashMap<String, Long>()
        params["sourceId"] = id

        return jdbc.update(SourceDaoSql.SQL_DELETE_BY_ID, params)
    }
}

class SourceDaoSql {
    companion object {
        const val SQL_SELECT_BY_ID = "SELECT id, name, reg_date, mod_date\n" +
                "FROM source\n" +
                "WHERE id=:sourceId;"

        const val SQL_SELECT_BY_NAME = "SELECT id, name, reg_date, mod_date\n" +
                "FROM source\n" +
                "WHERE name=:name;"

        const val SQL_DELETE_BY_ID = "DELETE FROM source\n" +
                "WHERE id=:sourceId;"
    }
}