package com.nanshan.we12talk.module.chat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nanshan.support.utils.DrawableUtils;
import com.nanshan.we12talk.module.BasePresenter;
import com.nanshan.we12talk.module.BaseToolBarActivity;
import com.nanshan.we12talk.R;
import com.nanshan.we12talk.module.chat.chat.ChatFragment;
import com.nanshan.we12talk.module.chat.contact.ContactFragment;
import com.nanshan.we12talk.module.chat.news.NewsFragment;
import com.nanshan.we12talk.module.chat.space.SpaceFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class MainActivity extends BaseToolBarActivity {

    @BindView(R.id.drawer)
    DrawerLayout mDrawerLayout;
    @BindViews({R.id.rl_chat, R.id.rl_contact, R.id.rl_news, R.id.rl_space})
    List<View> mNavigations;
    @BindView(R.id.vp_main)
    ViewPager mPager;

    //用于提示正在加载中的文字
    private TextView mTvLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public String onCreateTitle() {
        return getResources().getString(R.string.title_message);
    }

    @Override
    public Drawable getNavigationIcon() {
        return DrawableUtils.getDrawable(this, R.drawable.user_center_default_avatar, navigationWidth);
    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home) {
            //测滑信息
            mDrawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.rl_chat, R.id.rl_contact, R.id.rl_news, R.id.rl_space})
    public void onClick(View view) {
        for (View navigation : mNavigations) {
            navigation.setSelected(false);
        }
        view.setSelected(true);

        mPager.setCurrentItem(mNavigations.indexOf(view), false);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }

    private void init() {
        initNavigation();

        mPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), new Fragment[]{
                new ChatFragment(), new ContactFragment(), new NewsFragment(), new SpaceFragment()
        }));

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (View view : mNavigations) {
                    view.setSelected(false);
                }
                mNavigations.get(position).setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initLoadingNotice();
    }

    //初始化消息按钮为绿色
    private void initNavigation() {
        for (View view : mNavigations) {
            if (view.getId() == R.id.rl_chat) {
                view.setSelected(true);
            }
        }
    }

    private void initLoadingNotice() {
        mTvLoading = new TextView(this);
        mTvLoading.setGravity(Gravity.CENTER);
        mTvLoading.setText("正在加载中......");
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mTvLoading.setLayoutParams(params);
        ((ViewGroup) getWindow().getDecorView()).addView(mTvLoading);
    }
}
