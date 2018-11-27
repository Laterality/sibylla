package kr.latera.sibylla.authapi.service

interface AuthService {
    fun login(email: String, password: String, from: String): String?
    fun logout(token: String): Boolean
    fun verify(token: String): Boolean
}