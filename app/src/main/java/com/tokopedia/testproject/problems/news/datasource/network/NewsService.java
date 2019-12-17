package com.tokopedia.testproject.problems.news.datasource.network;

import com.tokopedia.testproject.problems.news.model.HeadlineResult;
import com.tokopedia.testproject.problems.news.model.NewsResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {
    @GET("everything")
    Observable<NewsResult> getEverything(@Query("q") String query, @Query("page") int offset, @Query("language") String lang, @Query("sortBy") String sort);

    @GET("top-headlines")
    Observable<HeadlineResult> getHeadline(@Query("country") String country);
}
