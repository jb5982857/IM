package com.nanshan.we12talk.module.chat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.nanshan.support.utils.LogUtils;

/**
 * Created by jiangbo on 2018/9/27.
 * 主页面的ViewPager adapter
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    private Fragment[] fragments;
    private FragmentManager fm;

    public MainPagerAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
        this.fm = fm;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        LogUtils.d("position " + position);
        Fragment fragment = null;
        fragment = fragments[position];
        Bundle bundle = new Bundle();
        bundle.putString("id", "" + position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LogUtils.d("instantiateItem " + position);
        Fragment fragment = (Fragment) super.instantiateItem(container,
                position);
        fm.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LogUtils.d("destroyItem " + position);
        Fragment fragment = fragments[position];
        fm.beginTransaction().hide(fragment).commit();
    }
}
