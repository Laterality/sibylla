package kr.latera.sibylla.articleapi.controller

import kr.latera.sibylla.articleapi.dto.ArticleDto
import kr.latera.sibylla.articleapi.dto.ArticleInsertDto
import kr.latera.sibylla.articleapi.dto.ArticleInsertResponseDto
import kr.latera.sibylla.articleapi.service.ArticleServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/")
class ArticleApiController {

    @Autowired
    private val articleService = ArticleServiceImpl()

    @PostMapping
    fun registerArticle(@RequestBody body: ArticleInsertDto): Any {
        val insertedId = articleService.addArticle(body)
        return ResponseEntity<Any>(ArticleInsertResponseDto("ok", "", insertedId), HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun retrieveArticle(@PathVariable("id") id: Long): ArticleDto? {
        return articleService.selectById(id)
    }

    @GetMapping("/list")
    fun listArticle(@RequestParam("limit") limit: Int?, @RequestParam("after") after: Long?, response: HttpServletResponse): Map<String, Any> {

        val articles = if (after == null) {
            articleService.selectArticles(limit ?: 20)
        } else {
            articleService.selectArticles(limit ?: 20, Date(after))
        }

        val resMap = HashMap<String, Any>()
        resMap["articles"] = articles

        response.setHeader("Access-Control-Allow-Origin", "*")

        return resMap
    }
}