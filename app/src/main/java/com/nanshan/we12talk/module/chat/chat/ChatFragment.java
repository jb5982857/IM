package com.nanshan.we12talk.module.chat.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nanshan.support.utils.LogUtils;
import com.nanshan.we12talk.R;
import com.nanshan.we12talk.module.BaseFragment;
import com.nanshan.we12talk.module.BasePresenter;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by jiangbo on 2018/9/27.
 * 聊天的fragment
 */

public class ChatFragment extends BaseFragment {
    @BindView(R.id.rv_chat)
    RecyclerView mRoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.d("onCreateView");
        return inflater.inflate(R.layout.chat_fragment_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mRoot.setAdapter(new ChatAdapter());
        //mRoot.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public BasePresenter onCreatePresenter() {
        return new ChatPresenter(this);
    }

    @Override
    public void loadData() {
        LogUtils.d("loadData");
    }
}
