package com.tokopedia.testproject.problems.news.datasource.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.tokopedia.testproject.problems.news.model.Article;
import com.tokopedia.testproject.problems.news.model.Headline;

@Database(entities = {Article.class, Headline.class},
        version = 2,
        exportSchema = false)
public abstract class ArticleDatabase extends RoomDatabase {
    public abstract ArticleDao articleDao();

    public abstract HeadlineDao headlineDao();
}
