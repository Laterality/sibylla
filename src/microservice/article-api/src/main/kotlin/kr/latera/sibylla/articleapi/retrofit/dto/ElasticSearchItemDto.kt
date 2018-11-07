package kr.latera.sibylla.articleapi.retrofit.dto

data class ElasticSearchItemDto<T>(
        val _index: String,
        val _type: String,
        val _id : String,
        val _score: Double,
        val _source: T)