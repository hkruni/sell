package com.imooc.netty;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

/**
 * @author
 */
public class SimpleClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //客户端收到ping表示服务端在验证客户端是否存活,长连接时使用
        if ("ping".equals(msg.toString())) {
            ctx.channel().writeAndFlush("ping\r\n");
            return;
        }

        System.out.println("msg : " + msg.toString());
        ctx.channel().attr(AttributeKey.valueOf("attr1")).set(msg);
        //ctx.channel().close(); 长连接时这里不能断开




//        Response response = JSONObject.parseObject(msg.toString(),Response.class);
//        DefaultFuture.receive(response);


    }
}