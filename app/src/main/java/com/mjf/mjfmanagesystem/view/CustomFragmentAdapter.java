package com.mjf.mjfmanagesystem.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mjf.mjfmanagesystem.R;

import java.util.List;

/**
 * Created by afs on 9/3/2015.
 * Fragment 状态保留
 */
public class CustomFragmentAdapter extends FragmentPagerAdapter {

    private FragmentManager mFragmentManager;
    private List<Fragment> listViews;

    public CustomFragmentAdapter(FragmentManager fm, List<Fragment> listViews) {
        super(fm);
        mFragmentManager = fm; // Cache this so we can use it later on...
        this.listViews = listViews;
    }

    @Override
    public int getCount() {
        return listViews.size();
    }

    @Override
    public Fragment getItem(int position) {
        String name = makeFragmentName(R.id.pager, position);
        Fragment fragment = mFragmentManager.findFragmentByTag(name);
        if (fragment == null) {
            return listViews.get(position);
        }
        return fragment;
    }

    private String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }
}
