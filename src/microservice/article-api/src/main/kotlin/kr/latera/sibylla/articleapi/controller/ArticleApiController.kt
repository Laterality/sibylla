package kr.latera.sibylla.articleapi.controller

import kr.latera.sibylla.articleapi.dto.ArticleInsertDto
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class ArticleApiController {

    @PostMapping
    fun registerArticle(@RequestBody body: Map<String, Any>) {
            TODO("Not Implemented yet")

    }
}