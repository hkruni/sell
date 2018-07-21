package com.imooc.service;

import com.imooc.dataobject.ProductCategory;
import com.imooc.dataobject.ProductInfo;
import com.imooc.util.KeyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test

    public void update() throws Exception {

        productService.updateProduct();

    }

//    public void increaseCatagoryAndProduct() {
//        ProductCategory productCategory = new ProductCategory();
//        productCategory.setCategoryName("衣服");
//        productCategory.setCategoryType(4);
//
//        ProductInfo productInfo = new ProductInfo();
//        productInfo.setProductId(KeyUtil.genUniqueKey());
//        productInfo.setProductName("杰克琼斯");
//        productInfo.setProductDescription("屌丝");
//        productInfo.setProductStock(80);
//        productInfo.setCategoryType(4);
//        productInfo.setProductIcon("/upload/file/pic");
//        productInfo.setProductPrice(new BigDecimal(324.6));
//
//        productService.increaseCatagoryAndProduct(productCategory,productInfo);
//
//
//    }

}