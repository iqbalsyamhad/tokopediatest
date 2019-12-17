package com.tokopedia.testproject.problems.news.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tokopedia.testproject.R;
import com.tokopedia.testproject.problems.news.model.Article;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<Article> articleList;
    private static String curdate;

    NewsAdapter(List<Article> articleList) {
        setArticleList(articleList);
    }

    void setArticleList(List<Article> articleList) {
        curdate = "";
        if (articleList == null) {
            this.articleList = new ArrayList<>();
        } else {
            this.articleList = articleList;
        }
    }

    void addArticleList(List<Article> articleList) {
        this.articleList.addAll(articleList);
    }

    void clearArticleList() {
        this.articleList.clear();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NewsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int i) {
        newsViewHolder.bind(articleList.get(i));
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvTitle;
        TextView tvDescription;
        TextView tvDate;
        TextView tvDateHead;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDateHead = itemView.findViewById(R.id.tvDatehead);
        }

        void bind(Article article) {
            Glide.with(itemView).load(article.getUrlToImage()).into(imageView);
            tvTitle.setText(article.getTitle());
            tvDescription.setText(article.getDescription());
            tvDate.setText(formattedDate(article.getPublishedAt()));

            if (curdate.equals(article.getPublishedAt().substring(0, 10))) {
                tvDateHead.setVisibility(View.GONE);
            } else {
                tvDateHead.setVisibility(View.VISIBLE);
                tvDateHead.setText(formattedDate(article.getPublishedAt()));
                curdate = article.getPublishedAt().substring(0, 10);
            }
            Log.d("currdate", "date " + curdate);
        }
    }

    private static String formattedDate(String inputDate) {
        String inputFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        String outputFormat = "d MMMM yyyy";

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);
        } catch (Exception e) {
            Log.e("formattedDateFromString", "Exception in formateDateFromstring(): " + e.getMessage());
        }
        return outputDate;
    }
}
