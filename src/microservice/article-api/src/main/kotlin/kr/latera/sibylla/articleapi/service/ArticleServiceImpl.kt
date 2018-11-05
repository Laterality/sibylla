package kr.latera.sibylla.articleapi.service

import kr.latera.sibylla.articleapi.dao.ArticleDao
import kr.latera.sibylla.articleapi.dao.ArticleImageDao
import kr.latera.sibylla.articleapi.dao.CrawledFromDao
import kr.latera.sibylla.articleapi.dao.SourceDao
import kr.latera.sibylla.articleapi.dto.ArticleDto
import kr.latera.sibylla.articleapi.dto.ArticleImageInsertDto
import kr.latera.sibylla.articleapi.dto.ArticleInsertDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
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

        val articleImageDao = ArticleImageDao(dataSource)
        article.images?.let {
            for (i in article.images ?: throw Exception("article's images changed to null while in loop")) {
                articleImageDao.insert(ArticleImageInsertDto(insertedArticleId, i))
            }
        }

        return insertedArticleId
    }

    @Transactional(readOnly=true)
    override fun selectById(articleId: Long): ArticleDto? {

        val article = ArticleDao(dataSource).selectById(articleId) ?: return null

        article.images = ArticleImageDao(dataSource).selectByArticleId(article.id)

        return article
    }

    @Transactional(readOnly=true)
    override fun selectByUid(uid: String): ArticleDto? {
        return ArticleDao(dataSource).selectByUid(uid)
    }

    @Transactional(readOnly=false)
    override fun deleteById(articleId: Long): Int {
        return ArticleDao(dataSource).deleteById(articleId)
    }

    @Transactional(readOnly=true)
    override fun selectArticles(limit: Int): List<ArticleDto> {
        val articleImageDto = ArticleImageDao(dataSource)
        val articles = ArticleDao(dataSource).selectList(limit)
        for (a in articles) {
            a.images = articleImageDto.selectByArticleId(a.id)
        }
        return articles
    }

    @Transactional(readOnly=true)
    override fun selectArticles(limit: Int, after: Date): List<ArticleDto> {
        val articleImageDto = ArticleImageDao(dataSource)
        val articles = ArticleDao(dataSource).selectList(limit, after)
        for (a in articles) {
            a.images = articleImageDto.selectByArticleId(a.id)
        }
        return articles
    }

}