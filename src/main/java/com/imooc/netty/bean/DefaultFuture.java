package com.imooc.netty.bean;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * 所有的Request共用一个DefaultFutrue对象
 */
public class DefaultFuture {

    final Lock lock = new ReentrantLock();

    public Condition condition = lock.newCondition();

    private Response response;

    //key为request id,value为DefaultFuture
    public static ConcurrentHashMap<Long,DefaultFuture> allDefault = new ConcurrentHashMap<>();

    public DefaultFuture(ClientRequest clientRequest) {
        allDefault.put(clientRequest.getId(),this);
    }

    /**
     * 客户端主线程同步等待获取数据,数据由channelRead线程提供
     * @return
     */
    public Response get(){
        lock.lock();
        try {
            while (!done()) {//response没有数据就会一直等待
                condition.await();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return this.response;
    }


    /**
     * 客户端channelRead线程中调用,接收到服务端响应,通知给主线程
     * @param response
     */
    public static void receive(Response response) {
        DefaultFuture df = allDefault.get(response.getId());

        if (df != null) {
            Lock lock = df.lock;
            lock.lock();
            try {
                df.setResponse(response);
                df.condition.signal();//通知get数据已等到
                allDefault.remove(df);
            }catch (Exception e) {

            }finally {
                lock.unlock();
            }

        }
    }

    private boolean done() {
        if (this.response != null) {
            return true;
        }
        return false;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
