package kr.latera.sibylla.articleapi

import kr.latera.sibylla.articleapi.dao.ArticleDao
import kr.latera.sibylla.articleapi.dto.ArticleInsertDto
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*
import javax.sql.DataSource

@RunWith(SpringRunner::class)
@SpringBootTest
class ArticleDaoTest{

    @Autowired
    private lateinit var dataSource: DataSource

    private var insertedId: Long = -1

    private val testArticleUrl = "http://localhost"
    private val testArticleUid = "12121212"
    private val testArticleTitle = "test article title"
    private val testArticleContent = "some awesome article content should be inserted here"
    private val testArticleWrittenDate = Date()

    @Test
    fun doTest() {

        // Test insert
        val testArticle = ArticleInsertDto(
                testArticleUrl,
                testArticleUid,
                testArticleTitle,
                testArticleContent,
                testArticleWrittenDate
        )

        insertedId = ArticleDao(dataSource).insert(testArticle)

        Assert.assertNotEquals(-1, insertedId)

        // Test select by id
        val selectedArticle = ArticleDao(dataSource).selectById(insertedId)

        Assert.assertNotNull(selectedArticle)

        if (selectedArticle != null) {
            Assert.assertEquals(testArticleUrl, selectedArticle.url)
            Assert.assertEquals(testArticleUid, selectedArticle.uid)
            Assert.assertEquals(testArticleTitle, selectedArticle.title)
            Assert.assertEquals(testArticleContent, selectedArticle.content)
            Assert.assertEquals(testArticleWrittenDate.time, selectedArticle.writtenDate.time)
        }

        // Test delete by id
        val affectedRows = ArticleDao(dataSource).deleteById(insertedId)

        Assert.assertEquals(1, affectedRows)
    }

}