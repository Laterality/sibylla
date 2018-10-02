package kr.latera.sibylla.userapi.dto

import java.util.*

data class UserDto(val id: Long,
                   val email: String,
                   val password: String,
                   val salt: String,
                   val regDate: Date,
                   val modDate: Date)

data class UserInsertDto(val email: String,
                   val password: String,
                   val salt: String)
