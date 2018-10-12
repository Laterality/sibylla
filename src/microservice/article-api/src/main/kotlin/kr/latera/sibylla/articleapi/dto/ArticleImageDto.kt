package kr.latera.sibylla.articleapi.dto

import java.util.*

data class ArticleImageDto(
        var id: Long,
        var articleId: Long,
        var src: String,
        var regDate: Date,
        var modDate: Date
)

data class ArticleImageInsertDto(
        var articleId: Long,
        var src: String
)