package kr.latera.sibylla.articleapi.dao

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class CrawledFromDao(dataSource: DataSource) {

    private val jdbc = NamedParameterJdbcTemplate(dataSource)
    private val insertAction = SimpleJdbcInsert(dataSource)
            .withTableName("crawled_from")
            .usingGeneratedKeyColumns("id")
            .usingColumns("article_id", "source_id")

    fun insert(articleId: Long, sourceId: Long): Long {
        val params = HashMap<String, Long>()

        params["article_id"] = articleId
        params["source_id"] = sourceId

        return insertAction.executeAndReturnKey(params).toLong()
    }
}