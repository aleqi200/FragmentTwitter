package com.codepath.apps.adroidtweet.listener;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.codepath.apps.adroidtweet.TwiterClient;
import com.codepath.apps.adroidtweet.adapter.TweetsArrayAdapter;
import com.codepath.apps.adroidtweet.data.TweetTimeLineHandler;

public class EndlessUserTimelineRecyclerOnScrollListener extends BaseEndlessRecyclerOnScrollListener {
    private String screenName;

    public EndlessUserTimelineRecyclerOnScrollListener(String screenName, LinearLayoutManager linearLayoutManager,
                                                       TwiterClient twiterClient,
                                                       TweetsArrayAdapter tweetsArrayAdapter,
                                                       Context context) {
        super(linearLayoutManager, twiterClient, tweetsArrayAdapter, context);
        this.screenName = screenName;
    }


    protected void onLoadMore(TweetTimeLineHandler handler) {
        client.getUserTimeline(screenName, lastId, handler);
    }
}