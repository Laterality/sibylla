package kr.latera.sibylla.articleapi.service

import kr.latera.sibylla.articleapi.dto.ArticleDto
import kr.latera.sibylla.articleapi.dto.ArticleInsertDto

interface ArticleService {
    fun addArticle(article: ArticleInsertDto): Long
    fun selectById(articleId: Long): ArticleDto?
    fun deleteById(articleId: Long): Int
}