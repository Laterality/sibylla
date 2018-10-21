package kr.latera.sibylla.articleapi.dto

import java.util.*

data class ReadDto(var id: Long,
                   var articleId: Long,
                   var readerId: Long,
                   var regDate: Date,
                   var modDate: Date)

data class ReadInsertDto(var articleId: Long,
                         var readerId: Long)