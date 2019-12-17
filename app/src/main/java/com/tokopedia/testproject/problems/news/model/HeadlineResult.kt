package com.tokopedia.testproject.problems.news.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HeadlineResult(
        @SerializedName("status")
        @Expose
        val status: String,
        @SerializedName("totalResults")
        @Expose
        val totalResults: Int,
        @SerializedName("articles")
        @Expose
        val articles: List<Headline>
)