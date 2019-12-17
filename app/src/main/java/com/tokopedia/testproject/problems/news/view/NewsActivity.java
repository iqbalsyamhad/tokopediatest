package com.tokopedia.testproject.problems.news.view;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tokopedia.testproject.R;
import com.tokopedia.testproject.problems.news.datasource.NewsRepository;
import com.tokopedia.testproject.problems.news.datasource.local.ArticleDatabase;
import com.tokopedia.testproject.problems.news.datasource.local.DBConstant;
import com.tokopedia.testproject.problems.news.datasource.local.LocalRepository;
import com.tokopedia.testproject.problems.news.datasource.network.NetworkRepository;
import com.tokopedia.testproject.problems.news.model.Article;
import com.tokopedia.testproject.problems.news.model.Headline;
import com.tokopedia.testproject.problems.news.presenter.NewsPresenter;
import com.tokopedia.testproject.problems.news.view.slider.SliderAdapter;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NewsActivity extends AppCompatActivity implements com.tokopedia.testproject.problems.news.presenter.NewsPresenter.View {

    private NewsPresenter newsPresenter;
    private NewsAdapter newsAdapter;
    private ViewPager mPager;
    private CirclePageIndicator indicator;
    private int currentPage = 0;
    private int NUM_PAGES = 0;
    private LinearLayout progressBar;
    private TextView errmsg;
    private Button more, retry;
    private int OFFSET = 0;
    private String KEYWORD = "android";

    protected NewsPresenter createPresenter(NewsPresenter.View view) {
        NetworkRepository networkRepository = new NetworkRepository();
        ArticleDatabase localdb = Room.databaseBuilder(getApplicationContext(), ArticleDatabase.class, DBConstant.DB_NAME).build();
        LocalRepository localRepository = new LocalRepository(localdb.articleDao(), localdb.headlineDao());
        NewsRepository newsRepository = new NewsRepository(localRepository, networkRepository);
        return new NewsPresenter(newsRepository, view);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mPager = findViewById(R.id.pager);
        indicator = findViewById(R.id.indicator);

        newsPresenter = createPresenter(this);
        newsAdapter = new NewsAdapter(null);
        errmsg = findViewById(R.id.tv_errmsg);
        more = findViewById(R.id.bt_more);
        retry = findViewById(R.id.bt_retry);
        progressBar = findViewById(R.id.progressBar);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setAdapter(newsAdapter);
        newsPresenter.getHeadline("id");
        loaddata(OFFSET);

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loaddata(OFFSET + 1);
            }
        });
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loaddata(OFFSET);
            }
        });
    }

    @Override
    public void onSuccessGetNews(List<Article> articleList, int newoffset) {
        errmsgState(false, "");
        if (newoffset == 0) {
            newsAdapter.setArticleList(articleList);
        } else {
            this.OFFSET = newoffset;
            newsAdapter.addArticleList(articleList);
        }
        if (articleList.size() < 20)
            more.setVisibility(View.GONE);
        else
            more.setVisibility(View.VISIBLE);
        retry.setVisibility(View.GONE);
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccessGetHeadline(List<Headline> headlineList) {
        updateSlider(headlineList);
    }

    @Override
    public void onErrorGetNews(Throwable throwable) {
        errmsgState(true, throwable.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        newsPresenter.unsubscribe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView sv = new SearchView(this.getSupportActionBar().getThemedContext());
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setActionView(sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadingState(true);
                newsAdapter.clearArticleList();
                OFFSET = 0;
                KEYWORD = query;
                newsPresenter.getEverything(KEYWORD, OFFSET);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equalsIgnoreCase("")) {
                    loadingState(true);
                    newsAdapter.clearArticleList();
                    OFFSET = 0;
                    newsPresenter.getEverything("android", OFFSET);
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        return false;
    }

    private void updateSlider(List<Headline> headlines) {
        mPager.setAdapter(new SliderAdapter(getApplicationContext(), headlines));
        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);

        NUM_PAGES = headlines.size();

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int pos) {
            }
        });
    }

    private void loaddata(int newoffset) {
        newsPresenter.getEverything(KEYWORD, newoffset);
    }

    @Override
    public void loadingState(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            if (newsAdapter.getItemCount() == 0) {
                errmsgState(true, "No data.");
            }
        }
    }

    private void errmsgState(boolean state, String message) {
        errmsg.setText(message);
        if (state) {
            errmsg.setVisibility(View.VISIBLE);
            retry.setVisibility(View.VISIBLE);
        } else {
            errmsg.setVisibility(View.GONE);
        }
    }
}
