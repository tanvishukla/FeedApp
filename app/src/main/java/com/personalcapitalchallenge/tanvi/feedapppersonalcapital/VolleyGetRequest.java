package com.personalcapitalchallenge.tanvi.feedapppersonalcapital;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanvi on 4/15/2017.
 */

public class VolleyGetRequest {

    private Context context;
    private FeedArticle feedArticle;
    public List<FeedArticle> feedDataList = new ArrayList<FeedArticle>();
    public String html=null;

    public VolleyGetRequest(Context context) {

        this.context = context;
    }


    public void restCall(final VolleyCallback volleyCallback) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String url = "https://blog.personalcapital.com/feed/?cat=3,891,890,68,284";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                html = response;


                Document document = Jsoup.parse(html);
                Element element = document.select("channel").first();

                Elements elements = element.getElementsByTag("item");

                for (Element el : elements) {
                    int link = el.toString().indexOf("<link />");
                    int comments = el.toString().indexOf("comments");
                    String url = el.toString().substring(link+8,comments-1);

                    String unescaped = el.getElementsByTag("description").text();
                    String desc = Jsoup.parse(unescaped).select("p").text();

                    feedArticle=new FeedArticle();
                    feedArticle.setFeedTitle(el.getElementsByTag("title").text());
                    feedArticle.setFeedArticleLink(url);
                    feedArticle.setFeedDescription(desc);
                    feedArticle.setFeedPubDate(el.getElementsByTag("pubDate").text());
                    feedArticle.setFeedImageURL(el.getElementsByTag("media:content").attr("url").toString());

                    feedDataList.add(feedArticle);

                }

                volleyCallback.onSuccess(feedDataList);

            }
        }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("No response !!!!!!!!!!!!!!!!!!!*******************");

            }
        });

        requestQueue.add(stringRequest);





    }

    public List<FeedArticle> parseHTML(String html){

        System.out.println(html);

        Document document = Jsoup.parse(html);
        Element element = document.select("channel").first();

        Elements elements = element.getElementsByTag("item");

        for (Element el : elements) {
            feedArticle=new FeedArticle();
            feedArticle.setFeedTitle(el.getElementsByTag("title").text());
            feedArticle.setFeedDescription(el.getElementsByTag("description").text());
            feedArticle.setFeedArticleLink(el.getElementsByTag("link").text());
            feedArticle.setFeedPubDate(el.getElementsByTag("pubDate").text());
            feedArticle.setFeedImageURL(el.getElementsByTag("media:content").text());

            feedDataList.add(feedArticle);

        }

        return feedDataList;

    }
}