package kr.latera.sibylla.articleapi.controller

import kr.latera.sibylla.articleapi.dto.ArticleInsertDto
import kr.latera.sibylla.articleapi.dto.ArticleInsertResponseDto
import kr.latera.sibylla.articleapi.service.ArticleServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class ArticleApiController {

    @Autowired
    private val articleService = ArticleServiceImpl()

    @PostMapping
    fun registerArticle(@RequestBody body: ArticleInsertDto): Any {
        println(body)
        val insertedId = articleService.addArticle(body)
        return ArticleInsertResponseDto(201, "", insertedId)
    }
}