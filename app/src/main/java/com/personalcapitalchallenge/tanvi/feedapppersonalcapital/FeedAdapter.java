package com.personalcapitalchallenge.tanvi.feedapppersonalcapital;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {

    private Context mContext;
    private List<FeedArticle> feedList;
    private final OnItemClickListener listener;

    private TextView textView, textView1;
    private NetworkImageView imageView;
    private CardView cardView;
    private LinearLayout linearLayout;


    //ViewHolder for Adapter
    static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,summary;
        public NetworkImageView thumbnail;


        public MyViewHolder(View view) {
            super(view);


            title = (TextView) view.findViewById(1);
            thumbnail = (NetworkImageView) view.findViewById(2);
            summary = (TextView) view.findViewById(3);


        }


        //Bind sets onclick listeners for each of the items in the view
        public void bind(final FeedArticle feedArticle, final OnItemClickListener listener) {

            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(feedArticle);
                }
            });

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(feedArticle);
                }
            });
        }
    }


    public FeedAdapter(Context mContext, List<FeedArticle> feedList, OnItemClickListener listener) {
        this.mContext = mContext;
        this.feedList = feedList;
        this.listener = listener;
    }



    //Create view and attach to viewholder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Main Layout
        linearLayout = new LinearLayout(mContext);
        ViewGroup.LayoutParams linearLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(linearLayoutParams);
        linearLayout.setBackgroundColor(Color.GRAY);

        //Card View
        cardView = new CardView(mContext);
        cardView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        cardView.setCardElevation(25);
        cardView.setRadius(0);
        cardView.setForegroundGravity(Gravity.CENTER);
        cardView.setFocusableInTouchMode(true);
        cardView.setUseCompatPadding(true);
        cardView.setBackgroundColor(Color.WHITE);

        //Sub view - Linear Layout
        LinearLayout linearLayout1 = new LinearLayout(mContext);
        linearLayout1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout1.setOrientation(LinearLayout.VERTICAL);

        //Linear Layout child 1 - NetworkImageView
        imageView = new NetworkImageView(mContext);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
        imageView.setBackgroundColor(R.attr.selectableItemBackground);
        imageView.setClickable(true);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setId(2);

        //Linear Layout child 2 - TextView
        textView = new TextView(mContext);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setTextColor(R.color.title);
        textView.setTextSize(17);
        textView.setTextColor(Color.BLACK);
        textView.setMaxLines(2);
        textView.setId(1);
        textView.setPadding(10,0,0,0);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setBackgroundColor(Color.WHITE);

        //Linear Layout child 3 - TextView
        textView1 = new TextView(mContext);
        textView1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView1.setTextSize(14);
        textView1.setEllipsize(TextUtils.TruncateAt.END);
        textView1.setMaxLines(2);
        textView1.setId(3);
        textView1.setTextColor(Color.BLACK);
        textView1.setBackgroundColor(Color.WHITE);

        //Add Children
        linearLayout1.addView(imageView);
        linearLayout1.addView(textView);
        linearLayout1.addView(textView1);

        //Add to Card View
        cardView.addView(linearLayout1);

        //Add to Main Layout
        linearLayout.addView(cardView);

        //Return this layout to new holder
        return new MyViewHolder(linearLayout);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        FeedArticle feed = feedList.get(position);

        holder.title.setText(feed.getFeedTitle());

        if(position == 0)
        {
            //Changes in layout for first article
            holder.summary.setText(feed.getFeedDescription());
            holder.title.setMaxLines(1);


        }


        //Load Image asynchronously using Volley Image Loader
        ImageLoader mImageLoader = VolleySingleton.getInstance(mContext).getImageLoader();
        holder.thumbnail.setImageUrl(feed.getFeedImageURL(),mImageLoader);


        //Bind item click listener
        holder.bind(feedList.get(position),listener);

    }



    @Override
    public int getItemCount() {
        return feedList.size();
    }



}
