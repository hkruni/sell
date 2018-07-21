package com.imooc.dao;

import com.imooc.dataobject.ProductInfo;
import com.imooc.util.KeyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoDaoTest {

    @Autowired
    private ProductInfoDao productInfoDao;

    @Test
    public void findOne() throws Exception {
    }

    @Test
    public void findByProductStatus() throws Exception {
    }

    @Test
    public void findAll() throws Exception {
    }

    @Test
    public void save() throws Exception {

        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(KeyUtil.genUniqueKey());
        productInfo.setProductName("iphone 8");
        productInfo.setProductDescription("加州制造");
        productInfo.setProductStock(80);
        productInfo.setCategoryType(3);
        productInfo.setProductIcon("/upload/file/pic");
        productInfo.setProductPrice(new BigDecimal(5000));
        productInfoDao.save(productInfo);

    }

    @Test
    public void update() throws Exception {
        ProductInfo productInfo = productInfoDao.findOne("1518960474411411927");
        Map<String,Object> map = new HashMap<String,Object>(){
            {
                put("product_id",productInfo.getProductId());
                put("product_price",new BigDecimal(1234));
                put("product_stock",2222);
                put("product_description","额代工");
            }
        };
        productInfoDao.update(map,productInfo.getProductId());

    }

}