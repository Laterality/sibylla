package kr.latera.sibylla.articleapi.service

import kr.latera.sibylla.articleapi.dao.ReadDao
import kr.latera.sibylla.articleapi.dto.ReadDto
import kr.latera.sibylla.articleapi.dto.ReadInsertDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.sql.DataSource

@Service
class ReadServiceImpl : ReadService {

    @Autowired
    private lateinit var dataSource: DataSource

    @Transactional(readOnly=false)
    override fun add(read: ReadInsertDto): Long {
        return ReadDao(dataSource).insert(read)
    }

    @Transactional(readOnly=true)
    override fun getByUserId(userId: Long, topn: Int): List<ReadDto> {
        return ReadDao(dataSource).selectByUserId(userId, topn)
    }

}