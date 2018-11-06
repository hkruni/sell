package com.imooc.netty.server;

import com.alibaba.fastjson.JSONObject;
import com.imooc.netty.bean.Response;
import com.imooc.netty.bean.ServerRequest;
import com.imooc.netty.spring.Media;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author
 */
public class SpringServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //获取到客户端请求
        ServerRequest request = JSONObject.parseObject(msg.toString(),ServerRequest.class);

        Media media = Media.newInstance();
        Response response = media.process(request);//反射调用controller方法



        ctx.channel().writeAndFlush(JSONObject.toJSONString(response));
        ctx.channel().writeAndFlush("\r\n");
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