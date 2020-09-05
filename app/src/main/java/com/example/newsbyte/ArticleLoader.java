package com.example.newsbyte;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.io.IOException;
import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<NewsArticle>> {

    //Variable to hold article's url
    private String newsURL;

    //Constructor for this class
    public ArticleLoader(Context context, String URL) {
        super(context);

        this.newsURL = URL;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();

    }


    @Override
    public List<NewsArticle> loadInBackground() {
        if (newsURL == null) {
            return null;
        }
        return QueryUtils.fetchNewsArticle(newsURL);
    }
}