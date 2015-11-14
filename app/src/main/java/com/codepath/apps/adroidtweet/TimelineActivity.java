package com.codepath.apps.adroidtweet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageSwitcher;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.adroidtweet.adapter.SmartFragmentStatePagerAdapter;
import com.codepath.apps.adroidtweet.fragments.HomeTimelineFragment;
import com.codepath.apps.adroidtweet.fragments.MentionsFragment;
import com.codepath.apps.adroidtweet.fragments.TweetsListFragment;

public class TimelineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_twiter);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TweetsPageAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(viewPager);
//        if (savedInstanceState == null) {
//            if (tweetsListFragment == null) {
//                tweetsListFragment = HomeTimelineFragment.newInstance();
//            }
//            if (mentionsFragment == null) {
//                mentionsFragment = MentionsFragment.newInstance();
//            }
//            addFragments();
//        }
    }

    private void addFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        // 2. create fragment transations
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // 3. using Transaction add/replace fragment
        //transaction.replace(R.id.frame_layout, tweetsListFragment);
        //transaction.replace(R.id.frame_layout, mentionsFragment);

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
        if (id == R.id.action_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivityForResult(intent, 200);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == RESULT_OK) {
            //tweetsListFragment.refreshTimeline();
        }
    }

    public void onSwitch(View view) {
        if (view instanceof ImageSwitcher && view.getId() == R.id.imgButnRet) {
            ImageSwitcher imageSwitcher = (ImageSwitcher) view;
            imageSwitcher.setImageResource(R.mipmap.ic_reply_press);
        }
    }

    public class TweetsPageAdapter extends SmartFragmentStatePagerAdapter {
        private static final int NUM_ITEMS = 2;
        private TweetsListFragment[] fragments = new TweetsListFragment[NUM_ITEMS];
        private final String[] titles = new String[]{"Home", "Mentions"};

        public TweetsPageAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            TweetsListFragment fragment = fragments[position];
            if (fragment == null && position == 0) {
                fragment = HomeTimelineFragment.newInstance();
                fragments[0] = fragment;
            } else if (fragment == null && position == 1) {
                fragment = MentionsFragment.newInstance();
                fragments[1] = fragment;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

    }
}

