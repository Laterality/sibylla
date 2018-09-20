package kr.latera.sibylla.articleapi.dao

import kr.latera.sibylla.articleapi.dto.ArticleDto
import kr.latera.sibylla.articleapi.dto.ArticleInsertDto
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.util.*
import javax.sql.DataSource

@Repository
class ArticleDao(dataSource: DataSource) {

    private val jdbc = NamedParameterJdbcTemplate(dataSource)

    private val insertAction = SimpleJdbcInsert(dataSource)
            .withTableName("article")
            .usingGeneratedKeyColumns("id")
            .usingColumns("url", "uid", "title", "content", "written_date")

    fun insert(article: ArticleInsertDto): Long {
        val params = HashMap<String, Any>()

        params["url"] = article.url
        params["uid"] = article.uid
        params["title"] = article.title
        params["content"] = article.content
        params["written_date"] = article.writtenDate

        return insertAction.executeAndReturnKey(params).toLong()
    }

    fun selectById(articleId: Long): ArticleDto? {
        val params = HashMap<String, Long>()
        params["articleId"] = articleId

        return jdbc.queryForObject(ArticleDaoSql.SELECT_BY_ID, params) {
            rs: ResultSet, _ -> ArticleDto(
                rs.getLong("id"),
                rs.getString("uid"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getString("url"),
                rs.getTimestamp("written_date"),
                rs.getTimestamp("reg_date"),
                rs.getTimestamp("mod_date"))
        }
    }

    fun deleteById(articleId: Long): Int {
        val params = HashMap<String, Long>()
        params["articleId"] = articleId

        return jdbc.update(ArticleDaoSql.DELETE_BY_ID, params)
    }
}

class ArticleDaoSql {
    companion object {
        const val SELECT_BY_ID =
                "SELECT id, uid, title, content, url, written_date, reg_date, mod_date\n" +
                        "FROM article\n" +
                        "WHERE id=:articleId;"

        const val DELETE_BY_ID =
                "DELETE FROM article\n" +
                        "WHERE id=:articleId;"
    }
}