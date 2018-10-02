package kr.latera.sibylla.userapi

import kr.latera.sibylla.userapi.dao.UserDao
import kr.latera.sibylla.userapi.dto.UserDto
import kr.latera.sibylla.userapi.dto.UserInsertDto
import kr.latera.sibylla.userapi.util.PasswordUtil
import org.junit.Assert
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import javax.sql.DataSource

@RunWith(SpringRunner::class)
@SpringBootTest
class UserDaoTest {

    @Autowired
    lateinit var dataSource: DataSource

    @Test
    @Ignore
    fun doTest() {
        val userDao = UserDao(dataSource)

        val email = "foo@bar.com"
        val changedEmail = "my@email.com"
        val plainPassword = "p@ssW0rd"
        val salt = PasswordUtil.makeSalt()
        val hash = PasswordUtil.encrypt(plainPassword, salt)

        val insertUser = UserInsertDto(email, hash, salt)

        val insertedId = userDao.insert(insertUser)

        var selectedById = userDao.selectById(insertedId)
        val selectedByEmail = userDao.selectByEmail(email)

        Assert.assertNotNull(selectedByEmail)
        Assert.assertNotNull(selectedById)
        if (selectedById != null) {
            Assert.assertEquals(email, selectedById.email)
            Assert.assertTrue(PasswordUtil.check(selectedById.password, plainPassword))
        }
        if (selectedByEmail != null) {
            Assert.assertEquals(email, selectedByEmail.email)
            Assert.assertTrue(PasswordUtil.check(selectedByEmail.password, plainPassword))
        }

        if (selectedById != null) {
            val updated = userDao.update(UserDto(
                    selectedById.id,
                    changedEmail,
                    selectedById.password,
                    selectedById.salt,
                    selectedById.regDate,
                    selectedById.modDate
            ))

            Assert.assertEquals(1, updated)

            selectedById = userDao.selectById(insertedId)

            if (selectedById != null) {
                Assert.assertEquals(changedEmail, selectedById.email)
            }
        }

        val affected = userDao.deleteUser(insertedId)
        Assert.assertEquals(1, affected)

    }
}