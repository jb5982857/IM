package com.nanshan.netty;


import android.content.Context;

import com.im2.common.protobuf.MessageTemplate;
import com.nanshan.support.utils.LogUtils;

import java.io.IOException;
import java.net.URI;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * Created by jiangbo on 2018/9/26.
 * WebSocket的开启类，开启长链接需要new这个类，并且传入URI
 * open()：进行长链接
 * close()：断开长链接
 * eval()：传递数据
 * 在链接过程中，遇到所有的情况实在WebSocketClientHandler类中回调和处理
 */

public class WebSocketClient {
    private Context mContext;
    private final URI uri;
    private Channel ch;
    private static final EventLoopGroup GROUP = new NioEventLoopGroup();
    private WebSocketClientHandler mHandler;

    public WebSocketClient(Context context, final String uri) {
        this.mContext = context;
        this.uri = URI.create(uri);
    }

    //对于这个方法的调用需要小心谨慎，只能在断开链接的时候调用，不能在外部随便调用，不然会出大事！！！
    public void open() throws Exception {
        Bootstrap b = new Bootstrap();
        String protocol = uri.getScheme();
        if (!"ws".equals(protocol)) {
            throw new IllegalArgumentException("Unsupported protocol: " + protocol);
        }

        // Connect with V13 (RFC 6455 aka HyBi-17). You can change it to V08 or V00.
        // If you change it to V00, ping is not supported and remember to change
        // HttpResponseDecoder to WebSocketHttpResponseDecoder in the pipeline.
        //创建一次新的握手
        mHandler =
                new WebSocketClientHandler(mContext,
                        WebSocketClientHandshakerFactory.newHandshaker(
                                uri, WebSocketVersion.V13, null, false, HttpHeaders.EMPTY_HEADERS, 1280000));

        //创建一个线程组，这个线程组的规则如下：
        //如果构造方法没有传入线程数，那么为cpu核数*2
        b.group(GROUP)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        //传输的协议 Protobuf
                        pipeline.addLast(new ProtobufVarint32FrameDecoder());
                        pipeline.addLast(new ProtobufDecoder(MessageTemplate.Server.getDefaultInstance()));
                        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                        pipeline.addLast(new ProtobufEncoder());
                        //处理消息
                        pipeline.addLast("ws-handler", mHandler);
                    }
                });
        //System.out.println("WebSocket Client connecting");
        ch = b.connect(uri.getHost(), uri.getPort()).sync().channel();
        mHandler.handshakeFuture().sync();
    }

    public void close() throws InterruptedException {
        //System.out.println("WebSocket Client sending close");
        ch.writeAndFlush(new CloseWebSocketFrame());
        ch.closeFuture().sync();
        //GROUP.shutdownGracefully();
    }

    //传递一个message数据
    public void eval(MessageTemplate.Client msg) throws IOException {
        LogUtils.v("send message");
        ch.writeAndFlush(msg);
    }

    public void ping() {
        LogUtils.v("ping");
        ch.writeAndFlush(MessageTemplate.Client.newBuilder().setType(MessageTemplate.ActionType.HEARTBEAT).build());
    }
}
