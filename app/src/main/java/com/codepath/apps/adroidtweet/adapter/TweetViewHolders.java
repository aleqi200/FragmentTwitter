package com.codepath.apps.adroidtweet.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;


import com.codepath.apps.adroidtweet.R;
import com.codepath.apps.adroidtweet.TimelineActivity;
import com.codepath.apps.adroidtweet.models.Tweet;
import com.squareup.picasso.Picasso;

public class TweetViewHolders extends RecyclerView.ViewHolder {

    private final ImageSwitcher mImageSwitcher;
    private TextView tvRTCount;
    private TextView tvFavCount;
    private Tweet tweet;
    private TextView tvUserName;
    private TextView tvUserId;
    private TextView tvInterval;
    public ImageView imageView;
    private TextView tvBody;
    private Context context;

    public TweetViewHolders(View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.ivProfileImage);
        tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
        tvUserId = (TextView) itemView.findViewById(R.id.tvUserId);
        tvBody = (TextView) itemView.findViewById(R.id.tvBody);
        tvInterval = (TextView) itemView.findViewById(R.id.tvInterval);
        tvRTCount = (TextView) itemView.findViewById(R.id.tvRTCount);
        tvFavCount = (TextView) itemView.findViewById(R.id.tvFavCount);
        mImageSwitcher = (ImageSwitcher) itemView.findViewById(R.id.imgButnRet);
    }


    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void process() {
        tvUserName.setText(tweet.getUser().getName());
        tvUserId.setText("@" + tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        tvInterval.setText(tweet.getCreatedInterval());
        tvFavCount.setText(String.valueOf(tweet.getFavoriteCount()));
        tvRTCount.setText(String.valueOf(tweet.getRetweetCount()));
        Picasso.with(context).load(tweet.getUser().getProfileImageUrl()).into(imageView);
        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(context);
                imageView.setImageResource(R.mipmap.ic_reply);
                return imageView;
            }
        });
    }
}