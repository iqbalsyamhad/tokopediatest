package com.tokopedia.testproject.problems.news.datasource.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.WorkerThread;

import com.tokopedia.testproject.problems.news.model.Headline;

import java.util.List;

@Dao
public interface HeadlineDao {
    @WorkerThread
    @Query("SELECT * FROM headlines ORDER BY publishedAt DESC LIMIT 20")
    List<Headline> getHeadline();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHeadline(List<Headline> headlines);
}
