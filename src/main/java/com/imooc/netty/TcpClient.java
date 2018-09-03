package com.imooc.netty;

import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 客户端采用长连接方式进行通信
 */
public class TcpClient {

    private static final Bootstrap b = new Bootstrap();
    private static ChannelFuture f = null;
    static {
        EventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workGroup)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch)
                            throws Exception {
                        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Delimiters.lineDelimiter()[0]));
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new SimpleClientHandler());
                        ch.pipeline().addLast(new StringEncoder());

                    }
                });
        try {
            f = b.connect("127.0.0.1",8888).sync();
        }catch (Exception e){

        }
    }

    //1.每一个请求都是同一个连接 2.每个请求都要有一个ID识别
    public static Response send(ClientRequest request){
        f.channel().writeAndFlush(JSONObject.toJSONString(request));
        f.channel().writeAndFlush("\r\n");
        DefaultFuture df = new DefaultFuture(request);

        return df.get();
    }

}
