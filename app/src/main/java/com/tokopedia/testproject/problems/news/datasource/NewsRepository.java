package com.tokopedia.testproject.problems.news.datasource;

import com.tokopedia.testproject.problems.news.datasource.local.LocalRepository;
import com.tokopedia.testproject.problems.news.datasource.network.NetworkRepository;
import com.tokopedia.testproject.problems.news.model.HeadlineResult;
import com.tokopedia.testproject.problems.news.model.NewsResult;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NewsRepository implements NewsRepo {
    LocalRepository localRepository;
    NetworkRepository networkRepository;


    public NewsRepository(LocalRepository localRepository, NetworkRepository networkRepository) {
        this.localRepository = localRepository;
        this.networkRepository = networkRepository;
    }

    @Override
    public Observable<NewsResult> getNews(String keyword, int offset) {
        return Observable.mergeDelayError(
                networkRepository.getArticles(keyword, offset).doOnNext(new Consumer<NewsResult>() {
                    @Override
                    public void accept(NewsResult result) throws Exception {
                        localRepository.insertNews(result);
                    }
                }).onErrorResumeNext(Observable.<NewsResult>empty()).subscribeOn(Schedulers.io()),
                localRepository.getNews(keyword, offset).subscribeOn(Schedulers.io())
        );
    }

    @Override
    public Observable<HeadlineResult> getHeadline(String country) {
        return Observable.mergeDelayError(
                networkRepository.getHeadline(country).doOnNext(new Consumer<HeadlineResult>() {
                    @Override
                    public void accept(HeadlineResult result) throws Exception {
                        localRepository.insertHeadline(result);
                    }
                }).onErrorResumeNext(Observable.<HeadlineResult>empty()).subscribeOn(Schedulers.io()),
                localRepository.getHeadline().subscribeOn(Schedulers.io())
        );
    }
}

interface NewsRepo {
    Observable<NewsResult> getNews(String keyword, int offset);

    Observable<HeadlineResult> getHeadline(String country);
}