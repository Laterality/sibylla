package kr.latera.sibylla.articleapi.service

import kr.latera.sibylla.articleapi.dao.ArticleDao
import kr.latera.sibylla.articleapi.dto.ArticleDto
import kr.latera.sibylla.articleapi.dto.ArticleInsertDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class ArticleServiceImpl : ArticleService {

    @Autowired
    private lateinit var dataSource: DataSource

    override fun addArticle(article: ArticleInsertDto): Long {
        return ArticleDao(dataSource).insert(article)
    }

    override fun selectById(articleId: Long): ArticleDto? {
        return ArticleDao(dataSource).selectById(articleId)
    }

    override fun deleteById(articleId: Long): Int {
        return ArticleDao(dataSource).deleteById(articleId)
    }

}