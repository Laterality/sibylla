package kr.latera.sibylla.articleapi.retrofit.service

import kr.latera.sibylla.articleapi.retrofit.dto.GetSimilaritiesResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProphetService {
    @GET("get-similarities")
    fun getSimilarities(@Query("article") articleId: Long): Call<GetSimilaritiesResponseDto>
}