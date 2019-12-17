package com.tokopedia.testproject.problems.news.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsResult(
        @SerializedName("status")
        @Expose
        val status: String,
        @SerializedName("totalResults")
        @Expose
        val totalResults: Int,
        @SerializedName("articles")
        @Expose
        val articles: List<Article>
)

data class Source(
        @SerializedName("id")
        @Expose
        val id: String,
        @SerializedName("name")
        @Expose
        val name: String
)