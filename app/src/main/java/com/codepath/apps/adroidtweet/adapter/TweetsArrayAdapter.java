package com.codepath.apps.adroidtweet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.adroidtweet.R;
import com.codepath.apps.adroidtweet.models.Tweet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by acampelo on 11/6/15.
 */
public class TweetsArrayAdapter extends RecyclerView.Adapter<TweetViewHolders> {

    private List<Tweet> tweets;
    private Context context;

    public TweetsArrayAdapter(Context context) {
        this.context = context;
        this.tweets = new ArrayList<>();
    }


    public void addAll(Collection<? extends Tweet> collection) {
        tweets.addAll(collection);
        notifyDataSetChanged();
    }

    public void clear() {
        tweets.clear();
    }

    @Override
    public TweetViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tweet, parent, false);
        TweetViewHolders rcv = new TweetViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(TweetViewHolders holder, int position) {
        holder.setTweet(tweets.get(position));
        holder.setContext(context);
        holder.process();
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public void addAllFirst(List<Tweet> newtweets) {
        tweets.addAll(0, newtweets);
        notifyDataSetChanged();
    }
}
