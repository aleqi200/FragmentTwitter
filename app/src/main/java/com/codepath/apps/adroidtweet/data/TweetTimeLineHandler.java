package com.codepath.apps.adroidtweet.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.adroidtweet.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by acampelo on 11/10/15.
 */
public abstract class TweetTimeLineHandler extends JsonHttpResponseHandler {

    private Context context;

    public TweetTimeLineHandler(Context context) {
        this.context = context;
    }

    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        List<Tweet> tweets = Tweet.fromJsonArray(response);
        processTweets(tweets);
    }

    protected abstract void processTweets(List<Tweet> tweets);

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        Log.e("error on request", errorResponse == null ? null : errorResponse.toString(), throwable);
        Toast.makeText(context, "failed request", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        Log.e("error on request", errorResponse == null ? null : errorResponse.toString(), throwable);
        Toast.makeText(context, "failed request", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        Log.e("error on request", responseString, throwable);
        Toast.makeText(context, "failed request", Toast.LENGTH_LONG).show();
    }
}
