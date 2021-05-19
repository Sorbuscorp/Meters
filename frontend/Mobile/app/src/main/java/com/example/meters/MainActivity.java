package com.example.meters;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    Singleton singletone = Singleton.INSTANCE;
    int PAGE_COUNT = 0;

    ViewPager2 pager;
    FragmentStateAdapter pagerAdapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(!singletone.isSessionSet) {
//            runSignIn();
//            return;
//        }

        setContentView(R.layout.activity_main);

        pager = (ViewPager2) findViewById(R.id.Pager);
        pagerAdapter = new MyFragmentPagerAdapter(this);
        pager.setAdapter(pagerAdapter);
    }
    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onStart() {
        super.onStart();
        if(!singletone.isSessionSet) {
            runSignIn();
        }
        else{
            try {
                if(singletone.meters.load())
                    PAGE_COUNT=singletone.meters.meterCollection.size()+1;
                else
                    PAGE_COUNT=0;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    protected void runSignIn(){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    private class MyFragmentPagerAdapter extends FragmentStateAdapter  {
        ArrayList<Meter> list = singletone.meters.meterCollection;

        public MyFragmentPagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            Fragment fragment = new FilledMeterFragment();
            Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(FilledMeterFragment.POS, position);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getItemCount() {
            return PAGE_COUNT;
        }
    }
}