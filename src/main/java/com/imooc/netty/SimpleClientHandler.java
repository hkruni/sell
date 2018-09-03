package com.imooc.netty;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author
 */
public class SimpleClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("msg : " + msg.toString());

//        if ("ping".equals(msg.toString())) {
//            ctx.channel().writeAndFlush("ping\r\n");
//            return;
//        }
        //ctx.channel().attr(AttributeKey.valueOf("sssss")).set(msg);
        //ctx.channel().close();

        Response response = JSONObject.parseObject(msg.toString(),Response.class);
        DefaultFuture.receive(response);


    }
}