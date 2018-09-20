package kr.latera.sibylla.articleapi.dto

import java.util.*

data class ArticleDto(var id: Long,
                      var uid: String,
                      var title: String,
                      var content: String,
                      var url: String,
                      var writtenDate: Date,
                      var regDate: Date,
                      var modDate: Date)

data class ArticleInsertDto(var url: String,
                       var uid: String,
                       var title: String,
                       var content: String,
                       var writtenDate: Date)