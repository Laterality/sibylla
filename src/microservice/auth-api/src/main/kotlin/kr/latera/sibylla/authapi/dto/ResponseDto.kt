package kr.latera.sibylla.authapi.dto

data class ResponseDto<out T>(val result: String,
                              val message: String,
                              val data: T?)