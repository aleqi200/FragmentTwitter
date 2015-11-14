package com.codepath.apps.adroidtweet.listener;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.codepath.apps.adroidtweet.TwiterClient;
import com.codepath.apps.adroidtweet.adapter.TweetsArrayAdapter;
import com.codepath.apps.adroidtweet.data.TweetTimeLineHandler;
import com.codepath.apps.adroidtweet.models.Tweet;

import java.util.List;

public class EndlessTimelineRecyclerOnScrollListener extends BaseEndlessRecyclerOnScrollListener {


    public EndlessTimelineRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager,
                                                   TwiterClient twiterClient,
                                                   TweetsArrayAdapter tweetsArrayAdapter,
                                                   Context context) {
        super(linearLayoutManager, twiterClient, tweetsArrayAdapter, context);
    }


    protected void onLoadMore(TweetTimeLineHandler handler) {
        client.getHomeTimeline(lastId, handler);
    }
}