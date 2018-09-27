package kr.latera.sibylla.articleapi.dto

import java.util.*

data class SourceDto(val id: Long,
                     val name: String,
                     val regDate: Date,
                     val modDate: Date)