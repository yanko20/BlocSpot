package com.yanko20.blocspot;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
