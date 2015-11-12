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
import com.codepath.apps.adroidtweet.TwiterClient;
import com.codepath.apps.adroidtweet.adapter.EndlessRecyclerOnScrollListener;
import com.codepath.apps.adroidtweet.adapter.TweetsArrayAdapter;
import com.codepath.apps.adroidtweet.data.TweetTimeLineHandler;
import com.codepath.apps.adroidtweet.models.Tweet;

import java.util.List;

/**
 * Created by acampelo on 11/11/15.
 */
public class TweetsListFragment extends Fragment {
    //inflation logic

    private TweetsArrayAdapter tweetsAdapter;
    private RecyclerView lvTweets;
    private LinearLayoutManager mLayoutManager;
    private EndlessRecyclerOnScrollListener listener;
    private long firstId = 1;

    private SwipeRefreshLayout swipeContainer;

    private TwiterClient client;

    public static TweetsListFragment newInstance(TwiterClient client) {

        Bundle args = new Bundle();
        TweetsListFragment fragment = new TweetsListFragment();
        fragment.client = client;
        fragment.setArguments(args);
        return fragment;
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
        listener = new EndlessRecyclerOnScrollListener(mLayoutManager, client, tweetsAdapter, getContext());
        lvTweets.addOnScrollListener(listener);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                refreshTimeline();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        populateTimeline();
        return view;
    }

    //creation logic

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void refreshTimeline() {
        client.getHomeTimelineStarting(firstId, new TweetTimeLineHandler(getContext()) {
            @Override

            protected void processTweets(List<Tweet> tweets) {
                updateFirstId(tweets);
                tweetsAdapter.addAllFirst(tweets);
                swipeContainer.setRefreshing(false);
            }
        });
    }

    public void populateTimeline() {
        client.getHomeTimeline(new TweetTimeLineHandler(getContext()) {
            @Override
            protected void processTweets(List<Tweet> tweets) {
                updateFirstId(tweets);
                updateLastId(tweets);
                tweetsAdapter.addAll(tweets);
            }
        });
    }

    private void updateFirstId(List<Tweet> tweets) {
        if (!tweets.isEmpty()) {
            firstId = tweets.get(0).getTweetId();
        }
    }

    public void updateLastId(List<Tweet> tweets) {
        if (!tweets.isEmpty()) {
            long lastId = tweets.get(tweets.size() - 1).getTweetId();
            listener.setLastId(lastId);
        }
    }

}
