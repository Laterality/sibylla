package kr.latera.sibylla.articleapi.retrofit.dto

data class ArticleSimilarityPair(val articleId: Long,
                                 val similarity: Double) : Comparable<ArticleSimilarityPair> {
    override fun compareTo(other: ArticleSimilarityPair): Int {
        return when {
            similarity < other.similarity -> -1
            similarity > other.similarity -> 1
            else -> 0
        }
    }
}