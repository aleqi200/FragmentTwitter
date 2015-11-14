package com.codepath.apps.adroidtweet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.adroidtweet.fragments.UserTimelineFragment;
import com.codepath.apps.adroidtweet.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        User profileGiven = getIntent().getParcelableExtra("user");
        TwiterClient client = TwiterApplication.getRestClient();

        if (profileGiven == null) {
            client.getUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJson(response);
                    populateUserProfile();
                }
            });
        } else {
            user = profileGiven;
            populateUserProfile();
        }
        if (savedInstanceState == null) {
            String screenName = profileGiven == null ? null : profileGiven.getScreenName();
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, userTimelineFragment).commit();
        }
    }

    private void populateUserProfile() {
        getSupportActionBar().setTitle("@" + user.getScreenName());
        final TextView followers = (TextView) findViewById(R.id.tvFollowers);
        final TextView following = (TextView) findViewById(R.id.tvFollowing);
        final TextView tweetCount = (TextView) findViewById(R.id.tvCountTweets);
        final TextView userDescription = (TextView) findViewById(R.id.user_tagline);
        final TextView userName = (TextView) findViewById(R.id.tvUserNameProfile);
        final ImageView profileImg = (ImageView) findViewById(R.id.imgUserProfile);
        followers.setText(user.getFollowers() + " followers");
        following.setText(user.getFollowing() + " following");
        tweetCount.setText(user.getTweetCount() + " tweets");
        userName.setText(user.getName());
        userDescription.setText(user.getDescription());
        Picasso.with(ProfileActivity.this).load(user.getProfileImageUrl()).into(profileImg);
    }
}
