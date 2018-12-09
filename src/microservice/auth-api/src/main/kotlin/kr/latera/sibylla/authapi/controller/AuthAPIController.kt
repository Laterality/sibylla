package kr.latera.sibylla.authapi.controller

import com.nimbusds.jwt.SignedJWT
import kr.latera.sibylla.authapi.dto.LoginRequestDto
import kr.latera.sibylla.authapi.dto.ResponseDto
import kr.latera.sibylla.authapi.service.AuthService
import kr.latera.sibylla.authapi.util.AuthUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@Controller
@RestController
@RequestMapping("/")
class AuthAPIController {

    @Autowired
    private lateinit var authService: AuthService

    @CrossOrigin
    @PostMapping("/login")
    fun login(@RequestBody body: LoginRequestDto, request: HttpServletRequest): ResponseEntity<ResponseDto<Any>> {
        val token = authService.login(body.email, body.password, request.getHeader("Origin"))
                ?: return ResponseEntity(ResponseDto("fail", "email or password is incorrect", null), HttpStatus.BAD_REQUEST)
        val resMap = HashMap<String, Any>()
        resMap["token"] = token
        return ResponseEntity(ResponseDto<Map<String, Any>>("ok", "", resMap), HttpStatus.OK)
    }

    @CrossOrigin
    @GetMapping("/logout")
    fun logout(request: HttpServletRequest): ResponseEntity<ResponseDto<Any>> {
        val authHeader = request.getHeader("Authorization")
                ?: return ResponseEntity(ResponseDto("fail", "Authorization header not included", null), HttpStatus.UNAUTHORIZED)

        return try {
            val done = authService.logout(authHeader.split(" ")[1])
            if (done) {
                ResponseEntity(ResponseDto("ok","" , null), HttpStatus.OK)
            } else {
                ResponseEntity(ResponseDto("fail", "logout failed", null), HttpStatus.INTERNAL_SERVER_ERROR)
            }

        } catch (e: Exception) {
            ResponseEntity(ResponseDto("error", "logout failed, may be server fault", null), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @CrossOrigin
    @GetMapping("/verify")
    fun verify(request: HttpServletRequest): ResponseEntity<ResponseDto<Any>> {
        val authHeader = request.getHeader("Authorization")

        val token = authHeader.split(" ")[1]

        val validity = authService.verify(token)

        return if (validity) {
            ResponseEntity(ResponseDto("ok", "", null), HttpStatus.OK)
        } else {
            ResponseEntity(ResponseDto("fail", "invalid token", null), HttpStatus.BAD_REQUEST)
        }
    }
}