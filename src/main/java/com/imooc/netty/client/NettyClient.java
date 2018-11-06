package com.imooc.netty.client;

import com.imooc.netty.SimpleClientHandler;
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
import io.netty.util.AttributeKey;

/**
 * Created by hukai on 2018/8/21.
 *
 * netty 客户端
 */
public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
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
                            ch.pipeline().addLast(new LongSimpleClientHandler());
                            ch.pipeline().addLast(new StringEncoder());

                        }
                    });

            //绑定端口同步等待成功
            ChannelFuture f = b.connect("127.0.0.1", 8888).sync();
            f.channel().writeAndFlush("hello,server");
            f.channel().writeAndFlush("\r\n");
            //等待服务端监听端口关闭
            f.channel().closeFuture().sync();

            Object result = f.channel().attr(AttributeKey.valueOf("attr1")).get();
            System.out.println("获取到服务器返回的数据=== " + result);

        } catch (Exception e) {

        }
    }
}
