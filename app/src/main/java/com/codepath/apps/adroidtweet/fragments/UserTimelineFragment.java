package com.codepath.apps.adroidtweet.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.adroidtweet.R;
import com.codepath.apps.adroidtweet.data.TweetTimeLineHandler;
import com.codepath.apps.adroidtweet.listener.BaseEndlessRecyclerOnScrollListener;
import com.codepath.apps.adroidtweet.listener.EndlessTimelineRecyclerOnScrollListener;
import com.codepath.apps.adroidtweet.listener.EndlessUserTimelineRecyclerOnScrollListener;

/**
 * Created by acampelo on 11/13/15.
 */
public class UserTimelineFragment extends TweetsListFragment {
    public UserTimelineFragment() {
        super(R.id.swipeContainer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, parent, savedInstanceState);
        return view;
    }

    @Override
    protected BaseEndlessRecyclerOnScrollListener createListener() {
        String screenName = getArguments().getString("screen_name");
        return new EndlessUserTimelineRecyclerOnScrollListener(screenName, mLayoutManager, client, tweetsAdapter, getContext());
    }

    public static UserTimelineFragment newInstance(String screenName) {
        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        UserTimelineFragment fragment = new UserTimelineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    protected void refresh(final SwipeRefreshLayout swipeContainer, TweetTimeLineHandler handler) {
        String screenName = getArguments().getString("screen_name");
        client.getUserTimelineStarting(screenName, firstId, handler);
    }

    public void populateTimeline(TweetTimeLineHandler handler) {
        String screenName = getArguments().getString("screen_name");
        client.getUserTimeline(screenName, handler);
    }
}
