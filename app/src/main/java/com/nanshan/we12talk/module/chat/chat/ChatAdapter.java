package com.nanshan.we12talk.module.chat.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jiangbo on 2018/9/30.
 * 聊天界面的Adapter
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ChatData> datas;
    private Context mContext;

    //脚布局的状态
    private ItemState itemState = ItemState.LOADING;
    //布局的类型
    private static final int TYPE_NORMAL = 1;
    //脚布局
    private static final int TYPE_FOOT = 2;
    //是否隐藏脚布局
    private boolean hideFoot;

    public ChatAdapter(Context context, List<ChatData> datas) {
        mContext = context;
        this.datas = datas;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount() && !hideFoot) {
            return TYPE_FOOT;
        }
        return TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : hideFoot ? datas.size() : datas.size() + 1;
    }

    //聊天item的viewHolder
    class ChatViewHolder extends RecyclerView.ViewHolder {

        public ChatViewHolder(View itemView) {
            super(itemView);
        }
    }

    //脚布局
    class FootViewHolder extends RecyclerView.ViewHolder{

        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 加载布局中显示的文字
     */
    public enum ItemState {
        //正在加载
        LOADING,
        //没有更多数据
        NOMORE;
    }
}
