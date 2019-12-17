package com.tokopedia.testproject.problems.news.presenter;

import com.tokopedia.testproject.problems.news.datasource.NewsRepository;
import com.tokopedia.testproject.problems.news.model.Article;
import com.tokopedia.testproject.problems.news.model.Headline;
import com.tokopedia.testproject.problems.news.model.HeadlineResult;
import com.tokopedia.testproject.problems.news.model.NewsResult;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hendry on 27/01/19.
 */
public class NewsPresenter {

    private NewsRepository newsRepository;
    private CompositeDisposable composite = new CompositeDisposable();
    private View view;

    public interface View {
        void onSuccessGetNews(List<Article> articleList, int newoffset);

        void onSuccessGetHeadline(List<Headline> headlineList);

        void onErrorGetNews(Throwable throwable);

        void loadingState(boolean state);
    }

    public NewsPresenter(NewsRepository newsRepository, NewsPresenter.View view) {
        this.view = view;
        this.newsRepository = newsRepository;
    }

    public void getEverything(String keyword, final int offset) {
        view.loadingState(true);
        newsRepository.getNews(keyword, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        composite.add(d);
                    }

                    @Override
                    public void onNext(NewsResult result) {
                        view.onSuccessGetNews(result.getArticles(), offset);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onErrorGetNews(e);
                    }

                    @Override
                    public void onComplete() {
                        view.loadingState(false);
                    }
                });
    }

    public void getHeadline(String country) {
        newsRepository.getHeadline(country)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HeadlineResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        composite.add(d);
                    }

                    @Override
                    public void onNext(HeadlineResult result) {
                        view.onSuccessGetHeadline(result.getArticles());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onErrorGetNews(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void unsubscribe() {
        composite.dispose();
    }
}
