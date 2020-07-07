package com.ldq.appinfo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ldq.appinfo.R;
import com.ldq.appinfo.consts.ConstKey;
import com.ldq.appinfo.fragment.AppDetailFragmentActivity;
import com.ldq.appinfo.fragment.AppDetailFragmentProvider;
import com.ldq.appinfo.fragment.AppDetailFragmentReceiver;
import com.ldq.appinfo.fragment.AppDetailFragmentService;
import com.ldq.appinfo.utils.AppInfoUtils;

public class AppDetailActivity extends AppCompatActivity implements
        android.support.v7.app.ActionBar.TabListener, OnPageChangeListener {

    private String[] tabTitles = {"Activity", "Service", "Receiver", "Provider"};
    private ViewPager mViewPager;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);

        mViewPager = findViewById(R.id.viewpager);
        TabFragmentPagerAdapter adapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(this);

        initTab();
    }

    private void initTab() {
        mActionBar = getSupportActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        tabTitles = AppInfoUtils.getTabTitle(getPackageManager(), getIntent().getStringExtra(ConstKey.KEY_PACKAGE_NAME));
        for (String title : tabTitles) {
            ActionBar.Tab tab = mActionBar.newTab().setText(title).setTabListener(this);
            mActionBar.addTab(tab);
        }
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction arg1) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction arg1) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(tab.getPosition());
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab arg0, FragmentTransaction arg1) {
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int position) {
        if (mActionBar != null) {
            mActionBar.setSelectedNavigationItem(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    class TabFragmentPagerAdapter extends FragmentPagerAdapter {

        public TabFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int index) {
            Fragment fragment = null;
            switch (index) {
                case 0:
                    fragment = new AppDetailFragmentActivity();
                    break;
                case 1:
                    fragment = new AppDetailFragmentService();
                    break;
                case 2:
                    fragment = new AppDetailFragmentReceiver();
                    break;
                case 3:
                    fragment = new AppDetailFragmentProvider();
                    break;
                default:
                    break;
            }
            Bundle bundle = new Bundle();
            String packageName = getIntent().getStringExtra(ConstKey.KEY_PACKAGE_NAME);
            bundle.putString(ConstKey.KEY_PACKAGE_NAME, packageName);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

    }

}
