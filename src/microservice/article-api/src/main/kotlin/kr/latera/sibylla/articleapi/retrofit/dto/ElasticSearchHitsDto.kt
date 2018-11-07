package kr.latera.sibylla.articleapi.retrofit.dto

data class ElasticSearchHitsDto<T>(val total: Int,
                                val max_score: Double,
                                val hits: Array<T>)