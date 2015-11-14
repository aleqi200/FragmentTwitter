package com.codepath.apps.adroidtweet.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.adroidtweet.R;
import com.codepath.apps.adroidtweet.TwiterApplication;
import com.codepath.apps.adroidtweet.TwiterClient;
import com.codepath.apps.adroidtweet.adapter.TweetsArrayAdapter;
import com.codepath.apps.adroidtweet.data.TweetTimeLineHandler;
import com.codepath.apps.adroidtweet.listener.BaseEndlessRecyclerOnScrollListener;
import com.codepath.apps.adroidtweet.models.Tweet;

import java.util.List;

/**
 * Created by acampelo on 11/11/15.
 */
public abstract class TweetsListFragment extends Fragment {
    //inflation logic

    protected TwiterClient client;
    protected TweetsArrayAdapter tweetsAdapter;
    protected RecyclerView lvTweets;
    protected LinearLayoutManager mLayoutManager;
    protected BaseEndlessRecyclerOnScrollListener listener;
    protected long firstId = 1;

    protected SwipeRefreshLayout swipeContainer;

    private int swipeContainerId;

    public TweetsListFragment(int swipeContainerId) {
        this.swipeContainerId = swipeContainerId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tweet_list, parent, false);
        lvTweets = (RecyclerView) view.findViewById(R.id.lvTweets);
        mLayoutManager = new LinearLayoutManager(getContext());
        lvTweets.setHasFixedSize(false);
        lvTweets.setLayoutManager(mLayoutManager);
        tweetsAdapter = new TweetsArrayAdapter(getContext());
        lvTweets.setAdapter(tweetsAdapter);
        listener = createListener();
        lvTweets.addOnScrollListener(listener);
        client = TwiterApplication.getRestClient();
        this.swipeContainer = (SwipeRefreshLayout) view.findViewById(swipeContainerId);
        this.swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        this.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh(TweetsListFragment.this.swipeContainer, getRefreshHandler());
            }
        });
        populateTimeline(getHandler());
        return view;
    }

    protected abstract void populateTimeline(TweetTimeLineHandler handler);

    protected abstract BaseEndlessRecyclerOnScrollListener createListener();

    /**
     * refresh the timeline on swipe to refresh
     *
     * @param swipeContainer
     */
    protected abstract void refresh(SwipeRefreshLayout swipeContainer, TweetTimeLineHandler handler);

    //creation logic

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void updateFirstId(List<Tweet> tweets) {
        if (!tweets.isEmpty()) {
            firstId = tweets.get(0).getTweetId();
        }
    }

    protected void updateLastId(List<Tweet> tweets) {
        if (!tweets.isEmpty()) {
            long lastId = tweets.get(tweets.size() - 1).getTweetId();
            listener.setLastId(lastId);
        }
    }

    private TweetTimeLineHandler getHandler() {
        return new TweetTimeLineHandler(getContext()) {
            @Override
            protected void processTweets(List<Tweet> tweets) {
                updateFirstId(tweets);
                updateLastId(tweets);
                tweetsAdapter.addAll(tweets);
            }
        };
    }
    private TweetTimeLineHandler getRefreshHandler() {
        return new TweetTimeLineHandler(getContext()) {
            @Override
            protected void processTweets(List<Tweet> tweets) {
                updateFirstId(tweets);
                tweetsAdapter.addAllFirst(tweets);
                swipeContainer.setRefreshing(false);
            }
        };
    }
}
