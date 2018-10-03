package kr.latera.sibylla.userapi.service

import kr.latera.sibylla.userapi.dao.UserDao
import kr.latera.sibylla.userapi.dto.UserDto
import kr.latera.sibylla.userapi.dto.UserInsertDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.sql.DataSource

@Service
class UserServiceImpl: UserService {

    @Autowired
    private lateinit var dataSource: DataSource

    @Transactional(readOnly=false)
    override fun register(user: UserInsertDto): Long {
        return UserDao(dataSource).insert(user)
    }

    @Transactional(readOnly=true)
    override fun getById(id: Long): UserDto? {
        return UserDao(dataSource).selectById(id)
    }

    @Transactional(readOnly=true)
    override fun getByEmail(email: String): UserDto? {
        return UserDao(dataSource).selectByEmail(email)
    }

    @Transactional(readOnly=false)
    override fun update(user: UserDto): Int {
        return UserDao(dataSource).update(user)
    }

}