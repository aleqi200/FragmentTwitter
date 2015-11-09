package com.codepath.apps.adroidtweet;

import android.content.Context;

import net.danlew.android.joda.JodaTimeAndroid;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 *
 *     RestClient client = TwiterApplication.getRestClient();
 *     // use client to send requests to API
 *
 */
public class TwiterApplication extends com.activeandroid.app.Application {
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		TwiterApplication.context = this;
		JodaTimeAndroid.init(context);
	}

	public static TwiterClient getRestClient() {
		return (TwiterClient) TwiterClient.getInstance(TwiterClient.class, TwiterApplication.context);
	}
}