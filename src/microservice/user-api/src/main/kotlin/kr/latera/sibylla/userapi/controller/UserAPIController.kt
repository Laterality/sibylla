package kr.latera.sibylla.userapi.controller

import kr.latera.sibylla.userapi.dto.ResponseDto
import kr.latera.sibylla.userapi.dto.UserInsertDto
import kr.latera.sibylla.userapi.service.UserService
import kr.latera.sibylla.userapi.service.UserServiceImpl
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Controller
@RestController
@RequestMapping("/")
class UserAPIController {

    private val userService: UserService = UserServiceImpl()

    @PostMapping("/register")
    fun registerUser(user: UserInsertDto): ResponseDto<Any?> {

        val insertedId = userService.register(user)
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