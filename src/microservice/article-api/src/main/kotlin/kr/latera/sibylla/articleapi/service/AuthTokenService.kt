package kr.latera.sibylla.articleapi.service

import kr.latera.sibylla.articleapi.dto.AuthTokenDto

interface AuthTokenService {
    fun getByToken(token: String): AuthTokenDto?
}