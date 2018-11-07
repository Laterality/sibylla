package kr.latera.sibylla.articleapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

@SpringBootApplication
class ArticleApiApplication

fun main(args: Array<String>) {
    runApplication<ArticleApiApplication>(*args)
}

@Bean
fun propertySourcePlaceholderConfiguerer(): PropertySourcesPlaceholderConfigurer {
    return PropertySourcesPlaceholderConfigurer()
}