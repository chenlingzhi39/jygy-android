package com.endeavour.jygy.parent.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.endeavour.jygy.R;
import com.endeavour.jygy.parent.fragment.BannerFragment;

import java.util.ArrayList;
import java.util.List;

public class DemoPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    public DemoPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentList = new ArrayList<>();
        fragmentList.add(BannerFragment.newInstance(R.drawable.banner2));
        fragmentList.add(BannerFragment.newInstance(R.drawable.banner3));
        fragmentList.add(BannerFragment.newInstance(R.drawable.banner4));
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}