package com.example.newsbyte;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsArticle>> {

    //Variables to hold News_loader ID and URL
    public static final int NEWS_LOADER_ID = 1;
    public static final String GUARDIAN_URL = "https://content.guardianapis.com/search?api-key=d020d78f-e57f-47a3-9f00-9a2c950a54f4";

    ListView mListView;
    TextView mTextView;
    NewsArticleAdapter mAdapter;
    ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        mListView = (ListView) findViewById(R.id.list);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mTextView = (TextView) findViewById(R.id.no_content);
        mAdapter = new NewsArticleAdapter(this, new ArrayList<NewsArticle>());
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //Obtain current position of news object
                NewsArticle currentArticle = mAdapter.getItem(position);

                //String URL to URI
                Uri articleUri = Uri.parse(currentArticle.getWebURL());

                //Create a intent
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, articleUri);

                //Launch the activity
                startActivity((websiteIntent));
            }
        });

        //Conditional statements for network connection
        if (NetworkUtils.isNetworkAvailable(this)) {
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mTextView.setVisibility(View.VISIBLE);
        }

    }

    @NonNull
    @Override
    public Loader<List<NewsArticle>> onCreateLoader(int id, @Nullable Bundle args) {

        //Building URL
        Uri baseUri = Uri.parse(GUARDIAN_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        //Setting Api Key and parameters
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("order-by", "relevance");

        return new ArticleLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<NewsArticle>> loader, List<NewsArticle> data) {
        mAdapter = new NewsArticleAdapter(this, data);
        mProgressBar.setVisibility(View.GONE);
        mListView.setAdapter(mAdapter);

        if(NetworkUtils.isNetworkAvailable(this) == null)
        {
            mTextView.setText(R.string.no_content);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<NewsArticle>> loader) {

    }
}