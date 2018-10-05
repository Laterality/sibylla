package kr.latera.sibylla.authapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthApiApplication

fun main(args: Array<String>) {
    runApplication<AuthApiApplication>(*args)
}
