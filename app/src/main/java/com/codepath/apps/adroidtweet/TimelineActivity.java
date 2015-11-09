package com.codepath.apps.adroidtweet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.adroidtweet.adapter.EndlessRecyclerOnScrollListener;
import com.codepath.apps.adroidtweet.adapter.TweetsArrayAdapter;
import com.codepath.apps.adroidtweet.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class TimelineActivity extends AppCompatActivity {

    private TwiterClient client;
    private TweetsArrayAdapter tweetsAdapter;
    private RecyclerView lvTweets;
    private LinearLayoutManager mLayoutManager;
    private EndlessRecyclerOnScrollListener listener;
    private long firstId = 1;

    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setLogo(R.drawable.ic_twiter);
        setSupportActionBar(toolbar);

        client = TwiterApplication.getRestClient();
        lvTweets = (RecyclerView) findViewById(R.id.lvTweets);
        mLayoutManager = new LinearLayoutManager(this);
        lvTweets.setHasFixedSize(false);
        lvTweets.setLayoutManager(mLayoutManager);
        tweetsAdapter = new TweetsArrayAdapter(this);
        lvTweets.setAdapter(tweetsAdapter);
        listener = new EndlessRecyclerOnScrollListener(mLayoutManager, client, tweetsAdapter, this);
        lvTweets.addOnScrollListener(listener);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
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
    }

    private void refreshTimeline() {
        client.getHomeTimelineStarting(firstId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                List<Tweet> tweets = Tweet.fromJsonArray(response);
                updateFirstId(tweets);
                tweetsAdapter.addAllFirst(tweets);
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("error on request", errorResponse == null ? null : errorResponse.toString(), throwable);
                Toast.makeText(TimelineActivity.this, "failed request", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.e("error on request", errorResponse == null ? null : errorResponse.toString(), throwable);
                Toast.makeText(TimelineActivity.this, "failed request", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("error on request", responseString, throwable);
                Toast.makeText(TimelineActivity.this, "failed request", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                List<Tweet> tweets = Tweet.fromJsonArray(response);
                updateFirstId(tweets);
                updateLastId(tweets);
                tweetsAdapter.addAll(tweets);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("error on request", errorResponse == null ? null : errorResponse.toString(), throwable);
                Toast.makeText(TimelineActivity.this, "failed request", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.e("error on request", errorResponse == null ? null : errorResponse.toString(), throwable);
                Toast.makeText(TimelineActivity.this, "failed request", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("error on request", responseString, throwable);
                Toast.makeText(TimelineActivity.this, "failed request", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateFirstId(List<Tweet> tweets) {
        if (!tweets.isEmpty()) {
            firstId = tweets.get(0).getTweetId();
        }
    }

    public void updateLastId(List<Tweet> tweets) {
        long lastId = tweets.get(tweets.size() - 1).getTweetId();
        listener.setLastId(lastId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_compose) {
            Intent intent = new Intent(this, ComposeActivity.class);
            startActivityForResult(intent, 200);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == RESULT_OK) {

            client.getHomeTimelineStarting(firstId, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                    List<Tweet> tweets = Tweet.fromJsonArray(response);
                    Log.d("build tweets", String.valueOf(tweets.size()));
                    if (!tweets.isEmpty()) {
                        firstId = tweets.get(0).getTweetId();
                    }
                    tweetsAdapter.addAllFirst(tweets);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("error on request", errorResponse == null ? null : errorResponse.toString(), throwable);
                    Toast.makeText(TimelineActivity.this, "failed request", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    Log.e("error on request", errorResponse == null ? null : errorResponse.toString(), throwable);
                    Toast.makeText(TimelineActivity.this, "failed request", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.e("error on request", responseString, throwable);
                    Toast.makeText(TimelineActivity.this, "failed request", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}

