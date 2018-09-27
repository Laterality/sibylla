package kr.latera.sibylla.articleapi.dto

data class ArticleInsertResponseDto(
        val result: String,
        val message: String,
        val id: Long
)