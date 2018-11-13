package kr.latera.sibylla.apigateway.filter

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class LoggingFilter : ZuulFilter() {

    companion object {
        var log: Logger = LoggerFactory.getLogger(LoggingFilter::class.java)
    }

    override fun run(): Any? {
        val ctx = RequestContext.getCurrentContext()
        val request = ctx.request

        log.info(String.format("[%s] %s", request.method, request.requestURL.toString()))

        return null
    }

    override fun shouldFilter(): Boolean {
        return true
    }

    override fun filterOrder(): Int {
        return 1
    }

    override fun filterType(): String {
        return "pre"
    }
}