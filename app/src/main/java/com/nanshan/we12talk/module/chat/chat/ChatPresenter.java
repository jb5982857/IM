package com.nanshan.we12talk.module.chat.chat;

import com.nanshan.we12talk.module.BaseModel;
import com.nanshan.we12talk.module.BasePresenter;
import com.nanshan.we12talk.module.IView;

/**
 * Created by jiangbo on 2018/9/30.
 * 聊天presenter
 */

public class ChatPresenter extends BasePresenter<ChatFragment, ChatModel> {
    public ChatPresenter(ChatFragment iView) {
        super(iView, new ChatModel());
    }
}
