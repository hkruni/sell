package com.imooc.dao;

import com.imooc.dataobject.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDaoTest {

    @Autowired
    private OrderMasterDao orderMasterDao;



    @Test
    public void findByBuyerOpenid() throws Exception {


    }

    @Test
    public void save() throws Exception {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1234567");
        orderMaster.setBuyerName("邹丽菲");
        orderMaster.setBuyerPhone("15117946727");
        orderMaster.setBuyerAddress("北京市海淀区");
        orderMaster.setBuyerOpenid("72h1xjayshas");
        orderMaster.setOrderAmount(new BigDecimal(178));

        orderMasterDao.save(orderMaster);


    }

}