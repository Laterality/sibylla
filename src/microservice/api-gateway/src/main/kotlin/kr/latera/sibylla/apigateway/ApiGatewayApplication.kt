package kr.latera.sibylla.apigateway

import kr.latera.sibylla.apigateway.filter.AuthFilter
import kr.latera.sibylla.apigateway.filter.LoggingFilter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableZuulProxy
class ApiGatewayApplication {

    @Bean
    fun loggingFilter(): LoggingFilter {
        return LoggingFilter()
    }

    @Bean
    fun authFilter(): AuthFilter {
        return AuthFilter()
    }

}

fun main(args: Array<String>) {
    runApplication<ApiGatewayApplication>(*args)
}
