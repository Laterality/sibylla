package kr.latera.sibylla.apigateway.retrofit_service

import kr.latera.sibylla.apigateway.dto.ResponseDto
import retrofit2.Call
import retrofit2.http.Header

interface AuthService {

    fun verify(@Header("Authorization") authHeader: String): Call<ResponseDto<Any>>
}
