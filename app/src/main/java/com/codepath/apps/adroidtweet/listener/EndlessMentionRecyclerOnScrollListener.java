package com.codepath.apps.adroidtweet.listener;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.codepath.apps.adroidtweet.TwiterClient;
import com.codepath.apps.adroidtweet.adapter.TweetsArrayAdapter;
import com.codepath.apps.adroidtweet.data.TweetTimeLineHandler;

public class EndlessMentionRecyclerOnScrollListener extends BaseEndlessRecyclerOnScrollListener {


    public EndlessMentionRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager,
                                                  TwiterClient twiterClient,
                                                  TweetsArrayAdapter tweetsArrayAdapter,
                                                  Context context) {
        super(linearLayoutManager, twiterClient, tweetsArrayAdapter, context);
    }


    protected void onLoadMore(TweetTimeLineHandler handler) {
        client.getMentionsTimeline(lastId, handler);
    }
}