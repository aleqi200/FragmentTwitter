package com.codepath.apps.adroidtweet;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwiterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
	public static final String REST_URL = "https://api.twitter.com/1.1";
	public static final String REST_CONSUMER_KEY = "MBq88f6mBKiQbSFgw0q5jde1C";
	public static final String REST_CONSUMER_SECRET = "4bCk9SZCrgri9n0tZvsjgpFkuX4NEb1ihh4woA7HQpGpBc6Cf8"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweet"; // Change this (here and in manifest)

	public TwiterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// CHANGE THIS

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
	public void getHomeTimeline(AsyncHttpResponseHandler handler) {
		getHomeTimelineStarting(1, handler);
	}

	public void getHomeTimeline(long sinceId, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("contributor_details", false);
		params.put("include_entities", false);
		params.put("max_id", sinceId);
		client.get(apiUrl, params, handler);
	}

	public void getHomeTimelineStarting(long sinceId, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("contributor_details", false);
		params.put("include_entities", false);
		params.put("since_id", sinceId);
		client.get(apiUrl, params, handler);
	}

	public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler) {
		getUserTimeline(screenName, 1, handler);
	}

	public void getUserTimeline(String screenName, long sinceId, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("contributor_details", false);
		params.put("include_entities", false);
		params.put("since_id", sinceId);
		params.put("screen_name", screenName);
		client.get(apiUrl, params, handler);
	}

	public void getUserTimelineStarting(String screenName, long sinceId, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("contributor_details", false);
		params.put("include_entities", false);
		params.put("max_id", sinceId);
		params.put("screen_name", screenName);
		client.get(apiUrl, params, handler);
	}

	public void getMentionsTimeline(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("contributor_details", false);
		params.put("include_entities", false);
		client.get(apiUrl, params, handler);
	}

	public void getMentionsTimeline(long sinceId, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("contributor_details", false);
		params.put("include_entities", false);
		params.put("max_id", sinceId);
		client.get(apiUrl, params, handler);
	}

	public void getMentionsTimelineStarting(long sinceId, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("since_id", sinceId);
		client.get(apiUrl, params, handler);
	}

	public void postStatus(String status, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("status", status);
		client.post(apiUrl, params, handler);
	}



	public void getUserInfo(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("account/verify_credentials.json");
		RequestParams params = new RequestParams();

		client.get(apiUrl, params, handler);
	}
}