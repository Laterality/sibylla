package kr.latera.sibylla.authapi.dto

import java.util.*

data class UserDto (val id: Long,
                    val email: String,
                    val password: String,
                    val salt: String,
                    val regDate: Date,
                    val modDate: Date)