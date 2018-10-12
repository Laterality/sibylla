package kr.latera.sibylla.articleapi.service

import kr.latera.sibylla.articleapi.dto.ArticleDto
import kr.latera.sibylla.articleapi.dto.ArticleImageDto
import kr.latera.sibylla.articleapi.dto.ArticleInsertDto
import java.util.*

interface ArticleService {
    fun addArticle(article: ArticleInsertDto): Long
    fun selectById(articleId: Long): ArticleDto?
    fun deleteById(articleId: Long): Int
    fun selectArticles(limit: Int): List<ArticleDto>
    fun selectArticles(limit: Int, after: Date): List<ArticleDto>
}