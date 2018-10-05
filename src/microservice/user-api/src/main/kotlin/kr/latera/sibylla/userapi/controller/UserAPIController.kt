package kr.latera.sibylla.userapi.controller

import kr.latera.sibylla.userapi.dto.ResponseDto
import kr.latera.sibylla.userapi.dto.UserAPIRequestDto
import kr.latera.sibylla.userapi.dto.UserInsertDto
import kr.latera.sibylla.userapi.service.UserService
import kr.latera.sibylla.userapi.util.PasswordUtil
import org.springframework.beans.factory.annotation.Autowired
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
    fun registerUser(@RequestBody body: UserAPIRequestDto): ResponseDto<Any?> {
        System.out.println(body)
        if (body.email == null ||
                body.password == null) {
            return ResponseDto("fail", "email or password is not included", null)
        }
        val salt = PasswordUtil.makeSalt()
        val insertedId = userService.register(UserInsertDto(body.email, PasswordUtil.encrypt(body.password, salt), salt))
        val user = userService.getById(insertedId)
        val resMap = HashMap<String, Any>()

        if (user == null) {
            return ResponseDto("error", "user isn't created", null)
        }

        resMap["id"] = user.id
        resMap["email"] = user.email

        return ResponseDto<Map<String, Any>>("ok", "", resMap)
    }
}