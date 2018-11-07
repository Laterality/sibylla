package kr.latera.sibylla.articleapi.retrofit.dto

data class ElasticSearchResponseDto<T>(
        val took: Long,
        val timed_out: Boolean,
        val hits: ElasticSearchHitsDto<T>)