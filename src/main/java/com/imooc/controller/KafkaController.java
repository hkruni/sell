package com.imooc.controller;

import com.imooc.dto.CartDTO;
import com.imooc.dto.Message;
import com.imooc.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;


@Controller
public class KafkaController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    @ResponseBody
    public String  sendKafka(HttpServletRequest request, HttpServletResponse response) {
        try {
            CartDTO cartDTO = new CartDTO("123",2);
            Message message = new Message();
            message.setId("12");
            message.setTime(new Date().getTime());
            message.setT(cartDTO);
            logger.info("kafka的消息={}", message);
            ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send("topic1", "key", JsonUtil.obj2String(message));
            //发送成功回调
            SuccessCallback<SendResult<String, String>> successCallback = new SuccessCallback<SendResult<String, String>>() {
                 @Override
                 public void onSuccess(SendResult<String, String> result) {
                     System.out.println("成功");
                 }
            };
            //发送失败回调
            FailureCallback failureCallback = new FailureCallback() {
                 @Override
                 public void onFailure(Throwable ex) {
                     System.out.println("失败");

                 }
            };
            listenableFuture.addCallback(successCallback, failureCallback);

            logger.info("发送kafka成功.");
            return "发送kafka成功";
        } catch (Exception e) {
            logger.error("发送kafka失败", e);
            return "发送kafka失败";
        }
    }
}
