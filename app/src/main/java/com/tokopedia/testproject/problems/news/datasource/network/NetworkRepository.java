package com.tokopedia.testproject.problems.news.datasource.network;

import com.tokopedia.testproject.BuildConfig;
import com.tokopedia.testproject.problems.news.model.HeadlineResult;
import com.tokopedia.testproject.problems.news.model.NewsResult;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkRepository implements ArticleNetworkRepo {
    private Retrofit retrofit;

    public NetworkRepository() {
        final Retrofit.Builder builder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(loggingInterceptor);
        }
        OkHttpClient okHttpClient = okHttpClientBuilder
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        return chain.proceed(chain.request()
                                .newBuilder()
                                .header("Authorization", BuildConfig.NEWS_API_KEY)
                                .build());
                    }
                })
                .build();
        retrofit = builder.baseUrl(NewsURL.BASE_URL).client(okHttpClient).build();
    }

    @Override
    public Observable<NewsResult> getArticles(String keyword, int offset) {
        return retrofit.create(NewsService.class).getEverything(keyword, (offset + 1), "en", "publishedAt");
    }

    @Override
    public Observable<HeadlineResult> getHeadline(String country) {
        return retrofit.create(NewsService.class).getHeadline(country);
    }
}

interface ArticleNetworkRepo {
    Observable<NewsResult> getArticles(String keyword, int offset);

    Observable<HeadlineResult> getHeadline(String country);
}
