package kr.latera.sibylla.articleapi.retrofit.dto

data class GetSimilaritiesResponseDto(val result: String,
                                      val similarities: List<String>,
                                      val articleIds: List<Long>)