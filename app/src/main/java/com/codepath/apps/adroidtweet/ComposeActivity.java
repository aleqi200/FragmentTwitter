package com.codepath.apps.adroidtweet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.adroidtweet.adapter.TextWatcherComposeMenu;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class ComposeActivity extends AppCompatActivity {

    private EditText editText;
    MenuItem countItem;
    MenuItem saveMenuItem;
    private TwiterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_twiter);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        editText = (EditText) findViewById(R.id.etCompose);
        client = TwiterApplication.getRestClient();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compose_menu, menu);
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            int id = menuItem.getItemId();
            if (id == R.id.action_tweet) {
                saveMenuItem = menuItem;
            }
            if (id == R.id.action_count) {
                countItem = menuItem;
            }
        }
        editText.addTextChangedListener(new TextWatcherComposeMenu(countItem, saveMenuItem));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_tweet) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void doTweet(MenuItem item) {
        client.postStatus(editText.getText().toString(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                setResult(FAILURE_MESSAGE);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                setResult(FAILURE_MESSAGE);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                setResult(FAILURE_MESSAGE);
                finish();
            }
        });

    }
}
