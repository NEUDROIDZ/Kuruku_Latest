package com.brise.tron.alphaverse_reborn.homepage;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.brise.tron.alphaverse_reborn.R;
import com.brise.tron.alphaverse_reborn.business.BusinessHome;
import com.brise.tron.alphaverse_reborn.items.HotMarket;
import com.brise.tron.alphaverse_reborn.items.ItemHome;
import com.brise.tron.alphaverse_reborn.messaging.MessagingHome;
import com.brise.tron.alphaverse_reborn.userprofile.CustomerList;
import com.brise.tron.alphaverse_reborn.userprofile.UserHome;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BaseActivity extends AppCompatActivity {

    private List<Integer> tabsAvailable = new ArrayList<>();

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabsAvailable.add(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onBackPressed() {

        if (tabsAvailable != null && tabsAvailable.size()>0 )
        {
            Objects.requireNonNull(tabLayout.getTabAt(0)).select();
            tabsAvailable.clear();
        } else {
            super.onBackPressed();
        }

        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Zerox homepage = new Zerox();
                    return homepage;
                case 1:
                    HotMarket hotMarket = new HotMarket();
                    return hotMarket;
                case 2:
                    MessagingHome messagingHome = new MessagingHome();
                    return messagingHome;
                case 3:
                    CustomerList mycontacts = new CustomerList();
                    return mycontacts;

                case 4:
                    BusinessHome businessHome = new BusinessHome();
                    return businessHome;

            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 5;
        }

    }
}
