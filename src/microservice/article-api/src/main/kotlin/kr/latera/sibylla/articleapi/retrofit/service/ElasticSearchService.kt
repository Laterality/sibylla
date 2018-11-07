package kr.latera.sibylla.articleapi.retrofit.service

import kr.latera.sibylla.articleapi.retrofit.dto.ElasticSearchArticle
import kr.latera.sibylla.articleapi.retrofit.dto.ElasticSearchResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ElasticSearchService {

    @GET("article/_search")
    fun searchArticle(@Query("q")query: String): Call<ElasticSearchResponseDto<ElasticSearchArticle>>
}