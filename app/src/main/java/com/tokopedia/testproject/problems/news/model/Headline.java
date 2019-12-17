package com.tokopedia.testproject.problems.news.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "headlines")
public class Headline {
    @SerializedName("source")
    @Expose
    @Ignore
    Source source;
    @SerializedName("author")
    @Expose
    @ColumnInfo(name = "author")
    public String author;
    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    public String title;
    @SerializedName("description")
    @Expose
    @ColumnInfo(name = "description")
    public String description;
    @SerializedName("url")
    @Expose
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "url")
    public String url;
    @SerializedName("urlToImage")
    @Expose
    @ColumnInfo(name = "urlToImage")
    public String urlToImage;
    @SerializedName("publishedAt")
    @Expose
    @ColumnInfo(name = "publishedAt")
    public String publishedAt;
    @SerializedName("content")
    @Expose
    @ColumnInfo(name = "content")
    public String content;

    public Headline() {
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
