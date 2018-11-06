package com.imooc.netty.client;

import com.alibaba.fastjson.JSONObject;
import com.imooc.netty.bean.DefaultFuture;
import com.imooc.netty.bean.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author
 */
public class LongSimpleClientHandler extends ChannelInboundHandlerAdapter {


    /**
     * 接收服务器返回的数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if ("ping".equals(msg.toString())) {
            ctx.channel().writeAndFlush("ping\r\n");
            return;
        }

        //客户端接收到服务端消息,序列化成response对象
        Response response = JSONObject.parseObject(msg.toString(),Response.class);
        //消息通知给客户端主线程
        DefaultFuture.receive(response);

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }
}