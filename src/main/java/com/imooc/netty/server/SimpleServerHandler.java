package com.imooc.netty.server;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author
 */
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {


    //到达消息的结束标志，才会调用read方法
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //1
        System.out.println(msg.toString());
        ctx.channel().writeAndFlush("is ok\r\n");
        //ctx.channel().close();


//        ServerRequest request = JSONObject.parseObject(msg.toString(),ServerRequest.class);
//        Response resp = new Response();
//        resp.setId(request.getId());//response的ID就是request的ID
//        resp.setResult("is ok");
//        ctx.channel().writeAndFlush(JSONObject.toJSONString(resp));
//        ctx.channel().writeAndFlush("\r\n");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent)evt;
            if(event.state().equals(IdleState.READER_IDLE)) {
                System.out.println("读空闲====");
                ctx.channel().close();//读空闲就需要把通道关闭,因为客户端已经不在了
            }else if (event.state().equals(IdleState.WRITER_IDLE)) {
                System.out.println("写空闲");
            }else if (event.state().equals(IdleState.ALL_IDLE)) {
                System.out.println("读写空闲");
                ctx.channel().writeAndFlush("ping\r\n");
            }
        }
    }



    //当添加了消息处理的handler，如lineBasedFrameDecoder或者FixLengthFramDecoder等，
    // 这样的话当消息没有到结束标志时，会进到complete方法里
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete");
    }
}