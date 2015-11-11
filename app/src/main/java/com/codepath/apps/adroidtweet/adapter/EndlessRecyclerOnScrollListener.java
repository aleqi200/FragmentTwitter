package com.codepath.apps.adroidtweet.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;


import com.codepath.apps.adroidtweet.TwiterClient;
import com.codepath.apps.adroidtweet.data.TweetTimeLineHandler;
import com.codepath.apps.adroidtweet.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    private static final int VISIBLE_THRESHOLD = 5; // The minimum amount of items to have below your current scroll position before loading more.

    private final Context context;
    private final TwiterClient client;
    private final TweetsArrayAdapter tweetsArrayAdapter;
    private final LinearLayoutManager layoutManager;
    private long lastId;


    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    int firstVisibleItem, visibleItemCount, totalItemCount;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager,
                                           TwiterClient twiterClient,
                                           TweetsArrayAdapter tweetsArrayAdapter,
                                           Context context) {
        this.layoutManager = linearLayoutManager;
        this.tweetsArrayAdapter = tweetsArrayAdapter;
        this.client = twiterClient;
        this.context = context;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = layoutManager.getItemCount();
        firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
            // End has been reached
            onLoadMore();

            loading = true;
        }
    }

    public void onLoadMore() {
        client.getHomeTimeline(lastId, new TweetTimeLineHandler(context) {
            @Override
            protected void processTweets(List<Tweet> tweets) {
                long lastId = tweets.get(tweets.size() - 1).getTweetId();
                EndlessRecyclerOnScrollListener.this.setLastId(lastId);
                tweetsArrayAdapter.addAll(tweets);
            }
        });
    }

    public void setLastId(long lastId) {
        this.lastId = lastId;
    }
}