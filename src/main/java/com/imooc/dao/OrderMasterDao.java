package com.imooc.dao;

import com.imooc.dataobject.OrderMaster;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMasterDao {

    public List<OrderMasterDao> findByBuyerOpenid(String buyerOpenid);

    public int save(OrderMaster orderMaster);
}
