package kr.latera.sibylla.authapi.dto

import java.util.*

data class AuthTokenDto(val id: Long,
                        val userId: Long,
                        val token: String,
                        val regDate: Date,
                        val modDate: Date)

data class AuthTokenInsertDto(val userId: Long,
                              val token: String)