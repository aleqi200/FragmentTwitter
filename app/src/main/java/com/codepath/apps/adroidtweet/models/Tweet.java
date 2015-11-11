package com.codepath.apps.adroidtweet.models;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acampelo on 11/6/15.
 */
@Table(name = "tweet")
public class Tweet extends Model {

    private static final DateTimeFormatter fmt = DateTimeFormat.forPattern("EEE MMM dd HH:mm:ss Z YYYY");
    @Column(name = "tweetId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private long tweetId;
    @Column(name = "body")
    private String body;
    @Column(name = "user", onUpdate = Column.ForeignKeyAction.SET_NULL, onDelete = Column.ForeignKeyAction.SET_NULL)
    private User user;
    @Column(name = "createdAt")
    private long createdAt;
    @Column(name = "retweetCount")
    private int retweetCount;
    @Column(name = "favoriteCount")
    private int favoriteCount;

    public long getTweetId() {
        return tweetId;
    }

    public String getBody() {
        return body;
    }

    public User getUser() {
        return user;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public String getCreatedInterval(){
        Period period = new Period(DateTime.now(), new DateTime(getCreatedAt()));

        if (period.getYears() != 0) {
            return Math.abs(period.getYears()) + "Y";
        }
        if (period.getMonths() != 0) {
            return Math.abs(period.getMonths()) + "M";
        }
        if (period.getDays() != 0) {
            return Math.abs(period.getDays()) + "d";
        }
        if (period.getHours() != 0) {
            return Math.abs(period.getHours()) + "h";
        }
        if (Math.abs(period.getMinutes()) > 3) {
            return Math.abs(period.getMinutes()) + "m";
        }
        return "now";
    }


    public static Tweet fromJson(JSONObject jsonObject) {
        Tweet tweet = null;
        try {
            tweet = new Tweet();
            tweet.body = jsonObject.getString("text");
            tweet.tweetId = jsonObject.getLong("id");
            tweet.createdAt = fmt.parseDateTime(jsonObject.getString("created_at")).getMillis();
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
            tweet.retweetCount = jsonObject.getInt("retweet_count");
            tweet.favoriteCount = jsonObject.getInt("favorite_count");
        } catch (JSONException e) {
            Log.e("error parsing object", jsonObject.toString(), e);
            return null;
        }
        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray response) {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                Log.d("contructing tweet", String.valueOf(i));
                JSONObject object = response.getJSONObject(i);
                Tweet tweet = Tweet.fromJson(object);
                if (tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                Log.e("invalid json response", e.getMessage());
            }
        }
        return tweets;
    }
}
