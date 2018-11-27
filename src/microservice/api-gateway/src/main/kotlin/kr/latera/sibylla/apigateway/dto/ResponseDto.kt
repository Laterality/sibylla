package kr.latera.sibylla.apigateway.dto

data class ResponseDto<out T>(val result: String,
                              val message: String,
                              val data: T)
