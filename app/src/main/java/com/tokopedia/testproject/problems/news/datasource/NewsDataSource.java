package com.tokopedia.testproject.problems.news.datasource;

import android.arch.paging.PagedList;

import com.tokopedia.testproject.problems.news.model.Article;

import io.reactivex.Observer;

public interface NewsDataSource {
    Observer<PagedList<Article>> getArticles(String keyword, int offset);
}
