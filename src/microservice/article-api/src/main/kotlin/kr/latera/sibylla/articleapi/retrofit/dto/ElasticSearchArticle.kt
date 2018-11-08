package kr.latera.sibylla.articleapi.retrofit.dto

import java.util.*

data class ElasticSearchArticle(
        val id: Long,
        val title: String,
        val content: String,
        val url: String,
        val uid: String,
        val written_date: Date,
        val reg_date: Date,
        val mod_date: Date)