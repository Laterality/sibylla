package kr.latera.sibylla.userapi.service

import kr.latera.sibylla.userapi.dto.UserDto
import kr.latera.sibylla.userapi.dto.UserInsertDto

interface UserService {
    fun register(user: UserInsertDto): Long
    fun getById(id: Long): UserDto?
    fun getByEmail(email: String): UserDto?
    fun update(user: UserDto): Int
}