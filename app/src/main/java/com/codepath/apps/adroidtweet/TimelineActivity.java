package com.codepath.apps.adroidtweet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageSwitcher;

import com.codepath.apps.adroidtweet.fragments.TweetsListFragment;

public class TimelineActivity extends AppCompatActivity {

    private TwiterClient client;
    private TweetsListFragment tweetsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_twiter);
        setSupportActionBar(toolbar);

        client = TwiterApplication.getRestClient();
        if (tweetsListFragment == null) {
            tweetsListFragment = TweetsListFragment.newInstance(client);
        }

        addFragments();
    }

    private void addFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        // 2. create fragment transations
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // 3. using Transaction add/replace fragment
        transaction.replace(R.id.frame_layout, tweetsListFragment);

        //4. commit txion
        transaction.commit();
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
            tweetsListFragment.refreshTimeline();
        }
    }

    public void onSwitch(View view) {
        if (view instanceof ImageSwitcher && view.getId() == R.id.imgButnRet) {
            ImageSwitcher imageSwitcher = (ImageSwitcher) view;
            imageSwitcher.setImageResource(R.mipmap.ic_reply_press);
        }
    }
}

