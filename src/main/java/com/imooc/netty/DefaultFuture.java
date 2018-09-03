package com.imooc.netty;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by hukai on 2018/8/22.
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

    //主线程获取数据,首先要等待结果
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

    //
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
