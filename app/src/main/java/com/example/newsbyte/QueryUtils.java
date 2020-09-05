package com.example.newsbyte;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    /**
     * Private constructor so that no one create a{@link QueryUtils} object.
     * This class will only hold static variables and methods and can be accessed
     * from the class name QueryUtils.
     */
    private QueryUtils(){

    }

    public static List<NewsArticle>  fetchNewsArticle(String newsURL) {
        if(newsURL == null)
        {
            return null;
        }

        URL url = createUrl(newsURL);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<NewsArticle> newsArticles = extractFeaturesFromJson(jsonResponse);

        return newsArticles;
    }

    private static List<NewsArticle> extractFeaturesFromJson(String jsonResponse) {

        if(jsonResponse == null)
        {
            return null;
        }

        //ArrayList to collect newsArticle objects
        List<NewsArticle> articles = new ArrayList<>();

        //Try and catch for parsing the JSON response if there are any issues
        try {
            JSONObject json = new JSONObject(jsonResponse);
            JSONObject response = json.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");


            //Loop to obtain properties of the articles
            for(int i = 0; i < results.length(); i++)
            {

                JSONObject currentNews = results.getJSONObject(i);
                JSONArray tags = currentNews.getJSONArray("tags");
                JSONObject currentTag = tags.getJSONObject(0);
                String title = currentNews.getString("webTitle");
                String section = currentNews.getString("sectionName");
                String date = currentNews.getString("webPublicationDate");
                String url = currentNews.getString("webUrl");
                String type = currentNews.getString("type");
                String author = currentTag.getString("webTitle");

                //Create NewsArticle object
                NewsArticle newsArticle = new NewsArticle(section, date, title, url, type, author);

                //Add to ArrayList
                articles.add(newsArticle);

            }
        } catch (JSONException e)
        {
            //Displays a message if there is a issue with parsing.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        //Return of the list of articles
        return articles;
    }

    private static String makeHttpRequest(URL url)  throws IOException{
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;


        if(url == null) {
            return jsonResponse;
        }

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if(inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream (InputStream inputStream) {
        InputStreamReader streamReader = null;
        BufferedReader reader = null;
        StringBuilder result = new StringBuilder();

        if(inputStream == null) {
            return null;
        }

        streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        reader = new BufferedReader(streamReader);
        try {
            String line = reader.readLine();
            while (line != null) {

                result.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    private static URL createUrl(String newsURL) {

        URL url = null;

        if(newsURL == null)
        {
            return url;
        }
        try
        {
            url = new URL(newsURL);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }


}
