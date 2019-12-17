package com.tokopedia.testproject.problems.news.datasource.local;

import com.tokopedia.testproject.problems.news.model.Article;
import com.tokopedia.testproject.problems.news.model.Headline;
import com.tokopedia.testproject.problems.news.model.HeadlineResult;
import com.tokopedia.testproject.problems.news.model.NewsResult;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

public class LocalRepository {
    private final ArticleDao mArticleDao;
    private final HeadlineDao mHeadlineDao;

    public LocalRepository(ArticleDao mArticleDao, HeadlineDao mHeadlineDao) {
        this.mArticleDao = mArticleDao;
        this.mHeadlineDao = mHeadlineDao;
    }

    public Observable<NewsResult> getNews(final String keyword, final int offset) {
        return Observable.fromCallable(new Callable<NewsResult>() {
            @Override
            public NewsResult call() throws Exception {
                List<Article> articles = mArticleDao.getArticles(keyword, offset);
                return new NewsResult("ok", articles.size(), articles);
            }
        });
    }

    public Observable<HeadlineResult> getHeadline() {
        return Observable.fromCallable(new Callable<HeadlineResult>() {
            @Override
            public HeadlineResult call() throws Exception {
                List<Headline> headlines = mHeadlineDao.getHeadline();
                return new HeadlineResult("ok", headlines.size(), headlines);
            }
        });
    }

    public void insertNews(NewsResult result) {
        mArticleDao.insertArticles(result.getArticles());
    }

    public void insertHeadline(HeadlineResult result) {
        mHeadlineDao.insertHeadline(result.getArticles());
    }
}
