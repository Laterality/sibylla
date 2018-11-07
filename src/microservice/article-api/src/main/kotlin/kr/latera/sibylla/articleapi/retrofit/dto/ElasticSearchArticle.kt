package kr.latera.sibylla.articleapi.retrofit.dto

import java.time.LocalDateTime

data class ElasticSearchArticle(
        val id: Long,
        val title: String,
        val content: String,
        val url: String,
        val uid: String,
        val written_date: LocalDateTime,
        val reg_date: LocalDateTime,
        val mod_date: LocalDateTime)