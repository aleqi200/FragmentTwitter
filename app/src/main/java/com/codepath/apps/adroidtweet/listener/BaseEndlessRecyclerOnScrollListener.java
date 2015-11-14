package com.codepath.apps.adroidtweet.listener;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.codepath.apps.adroidtweet.TwiterClient;
import com.codepath.apps.adroidtweet.adapter.TweetsArrayAdapter;
import com.codepath.apps.adroidtweet.data.TweetTimeLineHandler;
import com.codepath.apps.adroidtweet.models.Tweet;

import java.util.List;

public abstract class BaseEndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    private static final int VISIBLE_THRESHOLD = 5; // The minimum amount of items to have below your current scroll position before loading more.

    private final Context context;
    protected final TwiterClient client;
    private final TweetsArrayAdapter tweetsArrayAdapter;
    private final LinearLayoutManager layoutManager;
    protected long lastId;


    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    int firstVisibleItem, visibleItemCount, totalItemCount;

    public BaseEndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager,
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
            onLoadMore(getHandler());

            loading = true;
        }
    }

    protected abstract void onLoadMore(TweetTimeLineHandler handler);

    @NonNull
    private TweetTimeLineHandler getHandler() {
        return new TweetTimeLineHandler(context) {
            @Override
            protected void processTweets(List<Tweet> tweets) {
                long lastId = tweets.get(tweets.size() - 1).getTweetId();
                BaseEndlessRecyclerOnScrollListener.this.setLastId(lastId);
                tweetsArrayAdapter.addAll(tweets);
            }
        };
    }

    public void setLastId(long lastId) {
        this.lastId = lastId;
    }
}