package com.nanshan.we12talk.module.chat.chat;

/**
 * Created by jiangbo on 2018/9/30.
 * 消息界面单条数据的实体类
 */

public class ChatData {
    //讨论组或者好友昵称
    private String name;
    //最后一条消息，用户显示
    private String lastMsg;
    //icon地址
    private String iconUrl;
    //最后一条消息的时间
    private String time;
    //是否已读
    private boolean read;

    public ChatData(String name, String lastMsg, String iconUrl, String time, boolean read) {
        this.name = name;
        this.lastMsg = lastMsg;
        this.iconUrl = iconUrl;
        this.time = time;
        this.read = read;
    }

    public String getName() {
        return name;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getTime() {
        return time;
    }

    public boolean isRead() {
        return read;
    }
}
