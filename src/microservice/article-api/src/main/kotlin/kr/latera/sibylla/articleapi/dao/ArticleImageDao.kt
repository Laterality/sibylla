package kr.latera.sibylla.articleapi.dao

import kr.latera.sibylla.articleapi.dto.ArticleImageDto
import kr.latera.sibylla.articleapi.dto.ArticleImageInsertDto
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class ArticleImageDao(dataSource: DataSource) {

    private val jdbc = NamedParameterJdbcTemplate(dataSource)
    private val insertAction = SimpleJdbcInsert(dataSource)
            .withTableName("article_image")
            .usingColumns("article_id", "src")
            .usingGeneratedKeyColumns("id")

    private val articleImageRowMapper = RowMapper {
        rs, _ -> ArticleImageDto(
            rs.getLong("id"),
            rs.getLong("article_id"),
            rs.getString("src"),
            rs.getTimestamp("reg_date"),
            rs.getTimestamp("mod_date"))
    }

    fun insert(image: ArticleImageInsertDto): Long {
        val params = HashMap<String, Any>()

        params["article_id"] = image.articleId
        params["src"] = image.src

        return insertAction.executeAndReturnKey(params).toLong()
    }

    fun selectByArticleId(articleId: Long): List<ArticleImageDto> {
        val params = HashMap<String, Long>()
        params["articleId"] = articleId

        return jdbc.query(ArticleImageDaoSql.SELECT_BY_ARTICLE_ID, params, articleImageRowMapper)
    }

    class ArticleImageDaoSql {
        companion object {
            const val SELECT_BY_ARTICLE_ID =
                    "SELECT id, article_id, src, reg_date, mod_date\n" +
                            "FROM article_image\n" +
                            "WHERE article_id=:articleId;"
        }
    }
}