package com.codepath.apps.adroidtweet.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;


import com.codepath.apps.adroidtweet.TwiterClient;
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
        client.getHomeTimeline(lastId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("success", response.toString());
                List<Tweet> tweets = Tweet.fromJsonArray(response);
                Log.d("build tweets", String.valueOf(tweets.size()));
                long lastId = tweets.get(tweets.size() - 1).getTweetId();
                EndlessRecyclerOnScrollListener.this.setLastId(lastId);
                tweetsArrayAdapter.addAll(tweets);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("error on request", errorResponse == null ? null : errorResponse.toString(), throwable);
                Toast.makeText(context, "failed request", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.e("error on request", errorResponse == null ? null : errorResponse.toString(), throwable);
                Toast.makeText(context, "failed request", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("error on request", responseString, throwable);
                Toast.makeText(context, "failed request", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setLastId(long lastId) {
        this.lastId = lastId;
    }
}