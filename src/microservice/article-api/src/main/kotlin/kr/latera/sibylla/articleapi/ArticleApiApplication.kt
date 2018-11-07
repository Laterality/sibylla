package kr.latera.sibylla.articleapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

@SpringBootApplication
class ArticleApiApplication {
    @Bean
    fun propertySourcePlaceholderConfiguerer(): PropertySourcesPlaceholderConfigurer {
        return PropertySourcesPlaceholderConfigurer()
    }
}

fun main(args: Array<String>) {
    runApplication<ArticleApiApplication>(*args)
}