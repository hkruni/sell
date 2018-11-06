package com.imooc.netty.server;

import com.imooc.netty.spring.Constant;
import com.imooc.netty.zookeeper.ZookeeperFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 * Created by hukai on 2018/8/21.
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);//客户端连接线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();//客户端读写线程,默认线程个数为CPU核心数*2

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)//连接队列是128,当服务端处理不了过多请求,就把请求放到队列中
                    .childOption(ChannelOption.SO_KEEPALIVE,false)//不启动心跳保活机制
                    //.childAttr(AttributeKey.newInstance("childAttr"), "childAttrValue")
                    //.handler(new ServerHandler())
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(65535, Delimiters.lineDelimiter()[0]));
                            //解码器
                            ch.pipeline().addLast(new StringDecoder());

                            ch.pipeline().addLast(new IdleStateHandler(6,5,20, TimeUnit.SECONDS));
                            ch.pipeline().addLast(new LongServerHandler());
                            //编码器
                            ch.pipeline().addLast(new StringEncoder());


                        }
                    });

            ChannelFuture f = b.bind(8888).sync();

            //zookeeper注册
            CuratorFramework client = ZookeeperFactory.create();
            InetAddress inetAddress =InetAddress.getLocalHost();
            System.out.println(inetAddress.getHostAddress());//192.168.1.5
            client.create().withMode(CreateMode.EPHEMERAL).forPath(Constant.Server_PATH + inetAddress.getHostAddress());
            //zookeeper注册

            f.channel().closeFuture().sync();
        }catch (Exception e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
