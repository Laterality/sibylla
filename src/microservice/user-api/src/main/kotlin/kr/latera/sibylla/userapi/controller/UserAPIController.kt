package kr.latera.sibylla.userapi.controller

import kr.latera.sibylla.userapi.dto.ResponseDto
import kr.latera.sibylla.userapi.dto.UserAPIRequestDto
import kr.latera.sibylla.userapi.dto.UserInsertDto
import kr.latera.sibylla.userapi.service.UserService
import kr.latera.sibylla.userapi.util.PasswordUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Controller
@RestController
@RequestMapping("/")
class UserAPIController {

    @Autowired
    private lateinit var userService: UserService

    @PostMapping("/register")
    fun registerUser(@RequestBody body: UserAPIRequestDto): ResponseEntity<ResponseDto<Any?>> {
        System.out.println(body)
        if (body.email == null ||
                body.password == null) {
            return ResponseEntity(ResponseDto("fail", "email or password is not included", null), HttpStatus.BAD_REQUEST)
        }
        val salt = PasswordUtil.makeSalt()
        val insertedId = userService.register(UserInsertDto(body.email, PasswordUtil.encrypt(body.password, salt), salt))
        val user = userService.getById(insertedId)
        val resMap = HashMap<String, Any>()

        if (user == null) {
            return ResponseEntity(ResponseDto("error", "user isn't created", null), HttpStatus.INTERNAL_SERVER_ERROR)
        }

        resMap["id"] = user.id
        resMap["email"] = user.email

        return ResponseEntity(ResponseDto<Map<String, Any>>("ok", "", resMap), HttpStatus.OK)
    }
}