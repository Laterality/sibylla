package kr.latera.sibylla.articleapi.dto

import java.util.*

data class ArticleDto(var id: Long,
                      var uid: String,
                      var title: String,
                      var content: String,
                      var url: String,
                      var writtenDate: Date,
                      var sourceName: String, 
                      var regDate: Date,
                      var modDate: Date,
                      var images: List<ArticleImageDto>?)

data class ArticleInsertDto(var url: String,
                            var uid: String,
                            var title: String,
                            var content: String,
                            var writtenDate: Date,
                            var sourceName: String,
                            var images: Array<String>?) {

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}