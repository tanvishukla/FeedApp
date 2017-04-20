package com.personalcapitalchallenge.tanvi.feedapppersonalcapital;

import android.view.View;

import java.util.List;

/**
 * Created by tanvi on 4/16/2017.
 */

public interface VolleyCallback  {

    void OnCardClicked(View view, int position);

    void onSuccess(List<FeedArticle> feedDataList);
}
