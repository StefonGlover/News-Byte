package com.example.newsbyte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsArticleAdapter extends ArrayAdapter<NewsArticle> {

    //List to hold objects
    List<NewsArticle> newsArticles;


    public NewsArticleAdapter(@NonNull Context context, List<NewsArticle> newsArticleList) {
        super(context, 0, newsArticleList);

        this.newsArticles = newsArticleList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Obtain current position of the list
        NewsArticle current = newsArticles.get(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        String shortenDate = current.getPublicationDate().substring(0, 10);

        //Article's title textView
        TextView webTile = (TextView) convertView.findViewById(R.id.webTitle);
        webTile.setText(current.getWebTitle());

        //Publication date
        TextView date = (TextView) convertView.findViewById(R.id.date);
        date.setText("Date: "+shortenDate);

        //News section
        TextView section = (TextView) convertView.findViewById(R.id.section);
        section.setText("Section: " + current.getSectionName());

        //Type
        TextView type = (TextView) convertView.findViewById(R.id.type);
        type.setText("Type: "+current.getType());

        //Author
        TextView author = (TextView) convertView.findViewById(R.id.author_Name);
        author.setText("Author: "+current.getAuthor());

        return convertView;
    }
}
