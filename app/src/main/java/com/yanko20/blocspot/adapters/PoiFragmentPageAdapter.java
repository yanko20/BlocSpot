package com.yanko20.blocspot.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yanko20.blocspot.ui.PoiListFragment;
import com.yanko20.blocspot.ui.PoiMapFragment;

/**
 * Created by Yan Komizorov on 1/8/2017.
 */

public class PoiFragmentPageAdapter extends FragmentPagerAdapter {
    public PoiFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return position == 0 ? new PoiListFragment() : new PoiMapFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? "POI List" : "Map";
    }
}
