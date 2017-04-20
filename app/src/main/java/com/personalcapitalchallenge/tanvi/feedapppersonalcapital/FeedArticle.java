package com.personalcapitalchallenge.tanvi.feedapppersonalcapital;

/**
 * Created by tanvi on 4/13/2017.
 */

public class FeedArticle {

    private String feedTitle;
    private String feedImageURL;
    private String feedDescription;
    private String feedPubDate;
    private String feedArticleLink;

    public FeedArticle(){}


    public String getFeedTitle() {
        return feedTitle;
    }

    public void setFeedTitle(String feedTitle) {
        this.feedTitle = feedTitle;
    }

    public String getFeedImageURL() {
        return feedImageURL;
    }

    public void setFeedImageURL(String feedImageURL) {
        this.feedImageURL = feedImageURL;
    }

    public String getFeedDescription() {
        return feedDescription;
    }

    public void setFeedDescription(String feedDescription) {
        this.feedDescription = feedDescription;
    }

    public String getFeedPubDate() {
        return feedPubDate;
    }

    public void setFeedPubDate(String feedPubDate) {
        this.feedPubDate = feedPubDate;
    }

    public String getFeedArticleLink() {
        return feedArticleLink;
    }

    public void setFeedArticleLink(String feedArticleLink) {
        this.feedArticleLink = feedArticleLink;
    }
}


