package com.imooc.netty;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {



        ServerRequest request = JSONObject.parseObject(msg.toString(),ServerRequest.class);
        System.out.println("command : " + request.getCommand());
        Media media = Media.newInstance();

        Response response = media.process(request);
//        Response resp = new Response();
//        resp.setId(request.getId());//response的ID就是request的ID
//        resp.setResult("is ok");
        ctx.channel().writeAndFlush(JSONObject.toJSONString(response));
        ctx.channel().writeAndFlush("\r\n");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent)evt;
            if(event.state().equals(IdleState.READER_IDLE)) {
                System.out.println("读空闲");
                ctx.channel().close();
            }else if (event.state().equals(IdleState.WRITER_IDLE)) {
                System.out.println("写空闲");
                ctx.channel().close();


            }else if (event.state().equals(IdleState.ALL_IDLE)) {
                //System.out.println("读写空闲");
                //ctx.channel().writeAndFlush("ping\r\n");
            }
        }
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete");
    }
}