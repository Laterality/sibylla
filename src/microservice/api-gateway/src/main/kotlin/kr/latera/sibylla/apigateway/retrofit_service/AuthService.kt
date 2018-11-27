package kr.latera.sibylla.apigateway.retrofit_service

import kr.latera.sibylla.apigateway.dto.ResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface AuthService {

    @GET("verify")
    fun verify(@Header("Authorization") authHeader: String): Call<ResponseDto<Any>>
}
