package kr.latera.sibylla.articleapi.controller

import kr.latera.sibylla.articleapi.dto.*
import kr.latera.sibylla.articleapi.retrofit.dto.ArticleSimilarityPair
import kr.latera.sibylla.articleapi.retrofit.dto.GetSimilaritiesResponseDto
import kr.latera.sibylla.articleapi.retrofit.service.ElasticSearchService
import kr.latera.sibylla.articleapi.retrofit.service.ProphetService
import kr.latera.sibylla.articleapi.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
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

    @Value("\${elasticsearch.uri}")
    private lateinit var esUrl: String

    @PostMapping("/register")
    fun registerArticle(@RequestBody body: ArticleInsertDto): Any {
        if(articleService.selectByUid(body.uid) != null) {
            return ResponseEntity<Any>(ArticleInsertResponseDto("ok", "Article duplicates", -1), HttpStatus.OK)
        }
        val insertedId = articleService.addArticle(body)
        return ResponseEntity<Any>(ArticleInsertResponseDto("ok", "", insertedId), HttpStatus.CREATED)
    }

    @GetMapping("/by-id/{id}")
    fun retrieveArticle(@PathVariable("id") id: Long): ResponseEntity<ArticleDto?> {
        return ResponseEntity(articleService.selectById(id), HttpStatus.OK)
    }

    @GetMapping("/by-ids")
    fun retrieveArticles(@RequestParam("ids") ids: String): ResponseEntity<ResponseDto<List<ArticleDto>>> {
        val splitted = ids.split(",")
        val articles = ArrayList<ArticleDto>()

        for (str in splitted) {
            val article = articleService.selectById(str.toLong()) ?:
                    return ResponseEntity(ResponseDto("fail", "Invalid article id: $str", null), HttpStatus.BAD_REQUEST)
            articles.add(article)
        }

        return ResponseEntity(ResponseDto("ok", "", articles), HttpStatus.OK)
    }

    @GetMapping("/list")
    fun listArticle(@RequestParam("limit") limit: Int?, @RequestParam("after") after: Long?, response: HttpServletResponse): ResponseEntity<Map<String, Any>> {

        val articles = if (after == null) {
            articleService.selectArticles(limit ?: 20)
        } else {
            articleService.selectArticles(limit ?: 20, Date(after))
        }

        val resMap = HashMap<String, Any>()
        resMap["articles"] = articles

        response.setHeader("Access-Control-Allow-Origin", "*")

        return ResponseEntity(resMap, HttpStatus.OK)
    }

    @GetMapping("/recommends")
    fun recommendArticle(request: HttpServletRequest): ResponseEntity<ResponseDto<Any>> {
        // get user id
        val token = request.getHeader("Authorization").split(" ")[1]
        val authTokenDto = authTokenService.getByToken(token) ?:
            return ResponseEntity(ResponseDto("fail", "Invalid auth token", null), HttpStatus.UNAUTHORIZED)
        // get read info
        val reads = readService.getByUserId(authTokenDto.userId, 3)
        // get article similarity
        val retrofit = Retrofit.Builder()
                .baseUrl("http://altair.latera.kr/sb/api/prophet/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(ProphetService::class.java)

        val similarities = HashMap<Long, ArticleSimilarityPair>()

        for (read in reads) {
            val call = service.getSimilarities(read.articleId)
            val res = call.execute()
            if (res.isSuccessful) {
                val body = res.body() ?: continue

                for (i in body.articleIds.indices) {
                    similarities[body.articleIds[i]] = ArticleSimilarityPair(body.articleIds[i],
                            body.similarities[i].toDouble())
                }
            }
        }

        val list = ArrayList<ArticleSimilarityPair>()
        for (i in similarities.keys) {
            list.add(similarities[i] ?: throw Exception("Key is not in Map"))
        }
        // sort by similarity
        // and return
        val sorted = list.stream().sorted(ArticleSimilarityPair::compareTo).toList()

        return ResponseEntity(ResponseDto("ok",
                "",
                if (sorted.size > 1) {
                sorted.subList(0,
                        if (sorted.size > 100 ) { 100 } else { sorted.size -1 }) } else {
                    ArrayList()
                }), HttpStatus.OK)
    }

    @GetMapping("/read")
    fun registerRead(@RequestParam("articleId") articleId: Long,
                     request: HttpServletRequest): ResponseEntity<ResponseDto<Any>> {
        val token = request.getHeader("Authorization")?.split(" ")?.get(1) ?:
            return ResponseEntity(ResponseDto("fail", "Invalid authorization header", null), HttpStatus.UNAUTHORIZED)
        val auth = authTokenService.getByToken(token) ?:
            return ResponseEntity(ResponseDto("fail", "Authorization info doesn't exist", null), HttpStatus.UNAUTHORIZED)
        readService.add(ReadInsertDto(articleId, auth.userId))

        return ResponseEntity(ResponseDto("ok","", null), HttpStatus.OK)
    }

    @GetMapping("/search")
    fun search(@RequestParam("q", required = false)query: String?): ResponseEntity<ResponseDto<Any>> {
        if (query == null) {
            return ResponseEntity(ResponseDto("fail", "query is required", null), HttpStatus.BAD_REQUEST)
        }

        val r = Retrofit.Builder()
                .baseUrl(esUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = r.create(ElasticSearchService::class.java)

        val res = service.searchArticle(query).execute()

        return if (res.isSuccessful) {
            val hits = res.body()?.hits
            val articles = ArrayList<ArticleDto>()
            if (hits != null) {
                for (h in hits.hits) {
                    val article = articleService.selectById(h._source.id)
                    if (article != null) {
                        articles.add(article)
                    }
                }
            }
            ResponseEntity(ResponseDto("ok", "", articles), HttpStatus.OK)
        } else {
            ResponseEntity(ResponseDto("fail", "Search failed", null), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}