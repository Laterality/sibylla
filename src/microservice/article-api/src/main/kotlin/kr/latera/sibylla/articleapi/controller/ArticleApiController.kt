package kr.latera.sibylla.articleapi.controller

import kr.latera.sibylla.articleapi.dto.*
import kr.latera.sibylla.articleapi.retrofit.dto.ArticleSimilarityPair
import kr.latera.sibylla.articleapi.retrofit.dto.GetSimilaritiesResponseDto
import kr.latera.sibylla.articleapi.retrofit.service.ProphetService
import kr.latera.sibylla.articleapi.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.streams.toList

@RestController
@RequestMapping("/")
class ArticleApiController {

    @Autowired
    private lateinit var articleService: ArticleService
    @Autowired
    private lateinit var readService: ReadService
    @Autowired
    private lateinit var authTokenService: AuthTokenService

    @PostMapping
    fun registerArticle(@RequestBody body: ArticleInsertDto): Any {
        val insertedId = articleService.addArticle(body)
        return ResponseEntity<Any>(ArticleInsertResponseDto("ok", "", insertedId), HttpStatus.CREATED)
    }

    @GetMapping("/by-id/{id}")
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

    @GetMapping("/recommends")
    fun recommendArticle(request: HttpServletRequest): ResponseDto<Any> {
        // get user id
        val token = request.getHeader("Authorization").split(" ")[1]
        val authTokenDto = authTokenService.getByToken(token) ?: return ResponseDto("fail", "Invalid auth token", null)
        // get read info
        val reads = readService.getByUserId(authTokenDto.userId, 3)
        // get article similarity
        val retrofit = Retrofit.Builder()
                .baseUrl("http://altair.latera.kr/sb/api/prophet/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(ProphetService::class.java)

        var similarities = ArrayList<ArticleSimilarityPair>()

        for (read in reads) {
            val call = service.getSimilarities(read.articleId)
            val res = call.execute()
            if (res.isSuccessful) {
                val body = res.body() ?: continue

                for (i in body.articleIds.indices) {
                    similarities.add(ArticleSimilarityPair(body.articleIds[i], body.similarities[i].toDouble()))
                }
            }
        }

        // sort by similarity
        // and return
        val sorted = similarities.stream().sorted(ArticleSimilarityPair::compareTo).toList()

        return ResponseDto("ok",
                "",
                if (sorted.size > 1) {
                sorted.subList(0,
                        if (sorted.size > 100 ) { 100 } else { sorted.size -1 }) } else {
                    ArrayList()
                })
    }

    @GetMapping("/read")
    fun registerRead(@RequestParam("articleId") articleId: Long,
                     request: HttpServletRequest): ResponseDto<Any> {
        val token = request.getHeader("Authorization")?.split(" ")?.get(1) ?:
            return ResponseDto("fail", "Invalid authorization header", null)
        val auth = authTokenService.getByToken(token) ?:
            return ResponseDto("fail", "Authorization info doesn't exist", null)
        readService.add(ReadInsertDto(articleId, auth.userId))

        return ResponseDto("ok","", null)
    }

}