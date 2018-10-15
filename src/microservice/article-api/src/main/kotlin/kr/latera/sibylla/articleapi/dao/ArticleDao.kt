package kr.latera.sibylla.articleapi.dao

import kr.latera.sibylla.articleapi.dto.ArticleDto
import kr.latera.sibylla.articleapi.dto.ArticleInsertDto
import org.springframework.dao.EmptyResultDataAccessException
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

    private val articleRowMapper = RowMapper { rs: ResultSet, _ ->
        ArticleDto(
                rs.getLong("id"),
                rs.getString("uid"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getString("url"),
                rs.getTimestamp("written_date"),
                rs.getString("source_name"),
                rs.getTimestamp("reg_date"),
                rs.getTimestamp("mod_date"),
                null)
    }

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

        return try {
            jdbc.queryForObject(ArticleDaoSql.SELECT_BY_ID, params, articleRowMapper)
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    fun selectList(limit: Int): List<ArticleDto> {
        val params = HashMap<String, Int>()

        params["limit"] = limit

        return jdbc.query(ArticleDaoSql.SELECT_LIST, params, articleRowMapper)
    }

    fun selectList(limit: Int, after: Date): List<ArticleDto> {
        val params = HashMap<String, Any>()

        params["limit"] = limit
        params["after"] = after

        return jdbc.query(ArticleDaoSql.SELECT_LIST_WITH_WRITTEN_DATE, params, articleRowMapper)
    }

    fun deleteById(articleId: Long): Int {
        val params = HashMap<String, Long>()
        params["articleId"] = articleId

        return jdbc.update(ArticleDaoSql.DELETE_BY_ID, params)
    }

    class ArticleDaoSql {
        companion object {
            const val SELECT_BY_ID =
                    "SELECT article.id, uid, title, content, url, written_date, article.reg_date, article.mod_date, name as \"source_name\"\n" +
                            "FROM article, source, crawled_from\n" +
                            "WHERE crawled_from.article_id = article.id AND\n" +
                            "id=:articleId;"

            const val SELECT_LIST =
                    "SELECT article.id, uid, title, content, url, written_date, article.reg_date, article.mod_date, name as \"source_name\"\n" +
                            "FROM article, source, crawled_from\n" +
                            "WHERE crawled_from.article_id = article.id AND\n" +
                            "crawled_from.source_id = source.id\n" +
                            "ORDER BY written_date DESC\n" +
                            "LIMIT :limit;"

            const val SELECT_LIST_WITH_WRITTEN_DATE =
                    "SELECT article.id, uid, title, content, url, written_date, article.reg_date, article.mod_date, name as \"source_name\"\n" +
                            "FROM article, source, crawled_from\n" +
                            "WHERE written_date > :after AND\n" +
                            "crawled_from.article_id = article.id AND\n" +
                            "crawled_from.source_id = source.id\n" +
                            "ORDER BY written_date DESC\n" +
                            "LIMIT :limit;"

            const val DELETE_BY_ID =
                    "DELETE FROM article\n" +
                            "WHERE id=:articleId;"
        }
    }
}