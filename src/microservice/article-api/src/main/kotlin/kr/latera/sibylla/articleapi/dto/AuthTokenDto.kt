package kr.latera.sibylla.articleapi.dto

import java.util.*

data class AuthTokenDto(var id: Long,
                        var userId: Long,
                        var token: String,
                        var regDate: Date,
                        var modDate: Date)