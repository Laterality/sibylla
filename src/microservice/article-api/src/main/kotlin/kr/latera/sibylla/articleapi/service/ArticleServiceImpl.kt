package kr.latera.sibylla.articleapi.service

import kr.latera.sibylla.articleapi.dao.ArticleDao
import kr.latera.sibylla.articleapi.dao.CrawledFromDao
import kr.latera.sibylla.articleapi.dao.SourceDao
import kr.latera.sibylla.articleapi.dto.ArticleDto
import kr.latera.sibylla.articleapi.dto.ArticleInsertDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.sql.DataSource

@Service
class ArticleServiceImpl : ArticleService {

    @Autowired
    private lateinit var dataSource: DataSource

    @Transactional(readOnly=false)
    override fun addArticle(article: ArticleInsertDto): Long {
        val sourceDao = SourceDao(dataSource)
        val source = sourceDao.selectByName(article.sourceName)
        val sourceId = source?.id ?: sourceDao.insert(article.sourceName)
        val insertedArticleId = ArticleDao(dataSource).insert(article)

        CrawledFromDao(dataSource).insert(insertedArticleId, sourceId)

        return insertedArticleId
    }

    @Transactional(readOnly=true)
    override fun selectById(articleId: Long): ArticleDto? {
        return ArticleDao(dataSource).selectById(articleId)
    }

    @Transactional(readOnly=false)
    override fun deleteById(articleId: Long): Int {
        return ArticleDao(dataSource).deleteById(articleId)
    }

}