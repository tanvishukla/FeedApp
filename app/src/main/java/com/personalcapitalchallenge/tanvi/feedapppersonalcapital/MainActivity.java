package com.personalcapitalchallenge.tanvi.feedapppersonalcapital;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FeedAdapter adapter;
    private List<FeedArticle> albumList;
    private ProgressDialog progressDialog;
    private CoordinatorLayout coordinatorLayout;
    private RelativeLayout relativeLayout;
    private android.support.v7.app.ActionBar toolbar;
    private GridLayoutManager mLayoutManager;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


       //Main Layout
        coordinatorLayout = new CoordinatorLayout(this);
        ViewGroup.LayoutParams coOrdinatorLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        coordinatorLayout.setLayoutParams(coOrdinatorLayoutParams);
        coordinatorLayout.setFitsSystemWindows(true);


        //Child content layout
        relativeLayout = new RelativeLayout(this);
        ViewGroup.LayoutParams relativeLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(relativeLayoutParams);
        relativeLayout.setVerticalScrollBarEnabled(true);
        relativeLayout.setScrollBarSize(30);

        recyclerView = new RecyclerView(this);
        recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        recyclerView.setVerticalScrollBarEnabled(true);
        recyclerView.setScrollBarSize(30);
        recyclerView.setClipToPadding(false);
        relativeLayout.addView(recyclerView);


        coordinatorLayout.addView(relativeLayout);

        setContentView(coordinatorLayout);

        toolbar= getSupportActionBar();


        getFeed();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuItem menuItem = menu.add(Menu.NONE,1, Menu.NONE, "Refresh");
        menuItem.setIcon(R.drawable.refresh);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case 1:
                getFeed();
                return true;

            default:
                return false;
        }

    }


    //Function to get feed data
    private void getFeed() {

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Loading..");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        VolleyGetRequest volleyGetRequest = new VolleyGetRequest(this);

        //Async REST call to VolleyGetRequest
        volleyGetRequest.restCall(new VolleyCallback() {
            @Override
            public void onSuccess(List<FeedArticle> result) {

                albumList = result;
                adapter = new FeedAdapter(getApplicationContext(), albumList, new OnItemClickListener() {
                    @Override
                    public void onItemClick(FeedArticle feedArticle) {

                        Intent intent = new Intent(getApplicationContext(),ArticleActivity.class);
                        intent.putExtra("articleURL",feedArticle.getFeedArticleLink());
                        intent.putExtra("articleTitle",feedArticle.getFeedTitle());
                        startActivity(intent);

                    }
                });

                recyclerView.setAdapter(adapter);


                if(isTablet(getApplicationContext())){

                     mLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
                    mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){


                        @Override
                        public int getSpanSize(int position) {


                            return position == 0 ? 3 : 1;
                        }
                    });


                }
                else {

                     mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);

                    mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {


                        @Override
                        public int getSpanSize(int position) {


                            return position == 0 ? 2 : 1;
                        }
                    });

                }
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(15), true));
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                progressDialog.dismiss();

            }

            @Override
            public void OnCardClicked(View view, int position) {
                Log.d("OnClick", "Card Position" + position);
            }

        });



    }


    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing;
                }
            }
        }
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
