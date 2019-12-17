package com.tokopedia.testproject.problems.news.datasource.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.WorkerThread;

import com.tokopedia.testproject.problems.news.model.Article;

import java.util.List;

@Dao
public interface ArticleDao {
    @WorkerThread
    @Query("SELECT * FROM articles WHERE description LIKE '%' || :keyword || '%' ORDER BY publishedAt DESC LIMIT 20 OFFSET :offset")
    List<Article> getArticles(String keyword, int offset);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticles(List<Article> articles);

    @Update
    int updateArticle(Article article);
}
