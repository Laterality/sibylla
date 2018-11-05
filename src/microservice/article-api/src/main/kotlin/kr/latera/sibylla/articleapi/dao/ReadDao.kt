package kr.latera.sibylla.articleapi.dao

import kr.latera.sibylla.articleapi.dto.ReadDto
import kr.latera.sibylla.articleapi.dto.ReadInsertDto
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class ReadDao(dataSource: DataSource) {

    private val jdbc = NamedParameterJdbcTemplate(dataSource)
    private val insertAction = SimpleJdbcInsert(dataSource)
            .withTableName("read_article")
            .usingGeneratedKeyColumns("id")
            .usingColumns("article_id", "reader_id")

    private val readRowMapper = RowMapper {
        rs, _ -> ReadDto(
            rs.getLong("id"),
            rs.getLong("article_id"),
            rs.getLong("reader_id"),
            rs.getTimestamp("reg_date"),
            rs.getTimestamp("mod_date"))
    }

    fun insert(read: ReadInsertDto): Long {
        val params = HashMap<String, Long>()

        params["article_id"] = read.articleId
        params["reader_id"] = read.readerId

        return insertAction.executeAndReturnKey(params).toLong()
    }

    /**
     * @return List of ReadDto which user read ordered in read date
     */
    fun selectByUserId(readerId: Long, topn: Int): List<ReadDto> {
        val params = HashMap<String, Any>()

        params["readerId"] = readerId
        params["topn"] = topn

        return jdbc.query(ReadDaoSql.SELECT_BY_USER_ID, params, readRowMapper)
    }

    class ReadDaoSql {
        companion object {
            const val SELECT_BY_USER_ID =
                    "SELECT id, article_id, reader_id, reg_date, mod_date\n" +
                            "FROM read_article\n" +
                            "WHERE reader_id=:readerId\n" +
                            "ORDER BY reg_date ASC\n" +
                            "LIMIT :topn;"
        }
    }

}