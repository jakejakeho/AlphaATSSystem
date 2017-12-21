package com.petrasons.alphaatssystem;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.HashMap;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager = null;
    private MyPagerAdapter myPagerAdapter = null;
    private CircleIndicator indicator = null;
    private LoginSession mSession = null;

    private final int fragmentNum = 6;
    private boolean paused = false;
    private Thread getDataThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSession = new LoginSession(MainActivity.this);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                fragmentUpdate(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
        hideView(true);
        viewPager.setCurrentItem(1);

        getDataThread = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                while (!Thread.interrupted())
                    try
                    {
                        Thread.sleep(5000);
                        if(!paused){
                            mSession.getData(false,false);
                            mSession.getHistory(false,false);
                        }
                        // runOnUiThread to update the fragment that is the user is wathching
                        runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fragmentUpdate(viewPager.getCurrentItem());
                        }
                    });
                    }
                    catch (InterruptedException e)
                    {
                        // ooops
                    }
            }
        });
        getDataThread.start();
    }

    protected void hideView(boolean visibility) {
        if(visibility){
            viewPager.setVisibility(View.GONE);
            indicator.setVisibility(View.GONE);
        }else{
            viewPager.setVisibility(View.VISIBLE);
            indicator.setVisibility(View.VISIBLE);
        }
    }

    public void fragmentUpdate(int position){
        Fragment fragment = myPagerAdapter.getFragment(position);
        if (fragment != null && position == 0) {
            ((LogoutFragment) fragment).setFlag();
            fragment.onResume();
        } else if (fragment != null && position == 1) {
            ((SystemToggleFragment) fragment).setFlag();
            fragment.onResume();
        } else if (fragment != null && position == 2) {
            ((StrategiesFragment) fragment).setFlag();
            fragment.onResume();
        } else if (fragment != null && position == 3) {
            ((AccountSummaryFragment) fragment).setFlag();
            fragment.onResume();
        } else if (fragment != null && position == 4) {
            ((OrderFragment) fragment).setFlag();
            fragment.onResume();
        } else if (fragment != null && position == 5) {
            ((MessageFragment) fragment).setFlag();
            fragment.onResume();
        }
    }
    private class MyPagerAdapter extends FragmentPagerAdapter {
        private Map<Integer, String> mFragmentTags;
        private FragmentManager mFragmentManager;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentManager = fm;
            mFragmentTags = new HashMap<Integer, String>();
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {
                case 0: return LogoutFragment.newInstance(mSession);
                case 1: return SystemToggleFragment.newInstance(mSession);
                case 2: return StrategiesFragment.newInstance(mSession);
                case 3: return AccountSummaryFragment.newInstance(mSession);
                case 4: return OrderFragment.newInstance(mSession);
                case 5: return MessageFragment.newInstance(mSession);
                default: return LogoutFragment.newInstance(mSession);
            }
        }

        @Override
        public int getCount() {
            return fragmentNum;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object object = super.instantiateItem(container, position);
            if (object instanceof Fragment) {
                Fragment fragment = (Fragment) object;
                String tag = fragment.getTag();
                mFragmentTags.put(position, tag);
            }
            return object;
        }
        public Fragment getFragment(int position) {
            String tag = mFragmentTags.get(position);
            if (tag == null) {
                return null;
            }
            return mFragmentManager.findFragmentByTag(tag);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideView(true);
        paused = true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        mSession.getData(true, false);
        mSession.getHistory(false,false);
        fragmentUpdate(viewPager.getCurrentItem());
        paused = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDataThread.interrupt();
    }
}