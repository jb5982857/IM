package com.nanshan.netty;

import android.content.Context;
import android.content.Intent;

import com.im2.common.protobuf.MessageTemplate;
import com.nanshan.support.utils.LogUtils;
import com.nanshan.support.utils.ServiceUtils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.util.CharsetUtil;

/**
 * Created by jiangbo on 2018/9/26.
 * 这个类是用来接收和处理长链接过程中遇到的各种情况
 */

public class WebSocketClientHandler extends SimpleChannelInboundHandler<Object> {
    private Context mContext;
    private final WebSocketClientHandshaker handshaker;
    private ChannelPromise handshakeFuture;

    public WebSocketClientHandler(Context context, final WebSocketClientHandshaker handshaker) {
        this.handshaker = handshaker;
        this.mContext = context;
    }

    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }

    @Override
    public void handlerAdded(final ChannelHandlerContext ctx) throws Exception {
        LogUtils.v("handlerAdded " + ctx.channel().remoteAddress());
        handshakeFuture = ctx.newPromise();
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        LogUtils.v("channelActive " + ctx.channel().remoteAddress());
        handshaker.handshake(ctx.channel());
        startService(WebSocketServer.WHAT_PING);
        reconnectTime = 0;
    }

    //重连次数
    private int reconnectTime;

    //当长链接断开时回调这个方法
    @Override
    public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
        //System.out.println("WebSocket Client disconnected!");
        LogUtils.e("WebSocket Client disconnected " + ctx.channel().remoteAddress());
        //停止ping
        startService(WebSocketServer.WHAT_STOP_PING);
        if (reconnectTime == 5) {
            LogUtils.e("interrupt and reconnect 5 times already!");
            return;
        }
        //重连
        startService(WebSocketServer.WHAT_OPEN);
        reconnectTime++;
        LogUtils.i("reconnect " + reconnectTime + " times");
    }

    //这里接收长链接回调
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        LogUtils.v("channelRead0 " + msg.toString());
        final Channel ch = ctx.channel();
        if (!handshaker.isHandshakeComplete()) {
            // web socket client connected
            // handshaker.finishHandshake(ch, (FullHttpResponse) msg);
            handshakeFuture.setSuccess();
            return;
        }

        if (msg instanceof FullHttpResponse) {
            final FullHttpResponse response = (FullHttpResponse) msg;
            LogUtils.e("FullHttpResponse exception " + "Unexpected FullHttpResponse (getStatus=" + response.getStatus() + ", content="
                    + response.content().toString(CharsetUtil.UTF_8) + ')');
            throw new Exception("Unexpected FullHttpResponse (getStatus=" + response.getStatus() + ", content="
                    + response.content().toString(CharsetUtil.UTF_8) + ')');
        }

        if (msg instanceof MessageTemplate.Server) {
            LogUtils.v("receive msg " + msg.toString());
        }

    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        LogUtils.e("exceptionCaught " + ctx.channel().remoteAddress() + " cause " + cause.getMessage());
        cause.printStackTrace();

        if (!handshakeFuture.isDone()) {
            handshakeFuture.setFailure(cause);
        }

        ctx.close();
    }

    //是否正在链接中，需确认
    public boolean isConnected() {
        return handshaker.isHandshakeComplete();
    }

    private void startService(int type) {
        Intent intent = new Intent(mContext.getApplicationContext(), WebSocketServer.class);
        intent.putExtra(Constants.Key.TYPE, type);
        ServiceUtils.safetyStartService(mContext, intent);
    }
}
