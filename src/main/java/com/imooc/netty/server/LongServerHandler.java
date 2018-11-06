package com.imooc.netty.server;

import com.alibaba.fastjson.JSONObject;
import com.imooc.netty.bean.Response;
import com.imooc.netty.bean.ServerRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author  hukai
 */
public class LongServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("接收到客户端消息 :" + msg);

        //先把客户端的请求序列化成ServerRequest
        ServerRequest request = JSONObject.parseObject(msg.toString(),ServerRequest.class);

        //构造返回给客户端的Response对象
        Response response = new Response();
        response.setId(request.getId());//response的id和request的id要相同
        response.setResult("is ok");


//        System.out.println("command : " + request.getCommand());
//        Media media = Media.newInstance();
//        Response response = media.process(request);



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