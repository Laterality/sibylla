package kr.latera.sibylla.apigateway.filter

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import kr.latera.sibylla.apigateway.retrofit_service.AuthService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthFilter: ZuulFilter() {

    companion object {
        var log = LoggerFactory.getLogger(AuthFilter::class.java)
    }

    @Value("\${zuul.routes.auth.url}")
    private lateinit var authUrl: String

    override fun run(): Any? {
        val ctx = RequestContext.getCurrentContext()

        val authHeader = ctx.request.getHeader("Authorization")
        if (authHeader == null) {
            log.info("Auth not included")
            return null
        }

        val retrofit = Retrofit.Builder()
                .baseUrl("$authUrl/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(AuthService::class.java)

        val res = service.verify(authHeader).execute()

        if (res.isSuccessful) {
            if (res.code() == 400) {
                ctx.setSendZuulResponse(false)
                ctx.responseStatusCode = 401
            }
        } else {
            log.error("Failed to call verifying API")
        }

        return null
    }

    override fun shouldFilter(): Boolean {
        return true
    }

    override fun filterType(): String {
        return "pre"
    }

    override fun filterOrder(): Int {
        return 2
    }

}
