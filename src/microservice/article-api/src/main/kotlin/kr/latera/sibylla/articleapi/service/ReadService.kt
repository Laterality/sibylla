package kr.latera.sibylla.articleapi.service

import kr.latera.sibylla.articleapi.dto.ReadDto
import kr.latera.sibylla.articleapi.dto.ReadInsertDto

interface ReadService {
    fun add(read: ReadInsertDto): Long
    fun getByUserId(userId: Long, topn: Int): List<ReadDto>
}