package com.codepath.apps.adroidtweet.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.adroidtweet.R;
import com.codepath.apps.adroidtweet.TwiterApplication;
import com.codepath.apps.adroidtweet.TwiterClient;
import com.codepath.apps.adroidtweet.data.TweetTimeLineHandler;
import com.codepath.apps.adroidtweet.listener.BaseEndlessRecyclerOnScrollListener;
import com.codepath.apps.adroidtweet.listener.EndlessMentionRecyclerOnScrollListener;
import com.codepath.apps.adroidtweet.listener.EndlessTimelineRecyclerOnScrollListener;
import com.codepath.apps.adroidtweet.models.Tweet;

import java.util.List;

/**
 * Created by acampelo on 11/13/15.
 */
public class MentionsFragment extends TweetsListFragment {

    public MentionsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, parent, savedInstanceState);
        return view;
    }

    public static MentionsFragment newInstance() {
        Bundle args = new Bundle();
        MentionsFragment fragment = new MentionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BaseEndlessRecyclerOnScrollListener createListener() {
        return new EndlessMentionRecyclerOnScrollListener(mLayoutManager, client, tweetsAdapter, getContext());
    }

    protected void refresh(final SwipeRefreshLayout swipeContainer, TweetTimeLineHandler handler) {
        client.getMentionsTimelineStarting(firstId, handler);
    }

    protected void populateTimeline(TweetTimeLineHandler handler) {
        client.getMentionsTimeline(handler);
    }
}
