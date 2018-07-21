package com.imooc.dao;

import com.imooc.dataobject.OrderDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailDao {

    public List<OrderDetail> findByOrderId(String  orderid);
}
