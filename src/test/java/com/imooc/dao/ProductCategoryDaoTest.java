package com.imooc.dao;

import com.imooc.dataobject.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void insertProductCategory() throws Exception {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("图书");
        productCategory.setCategoryType(8);
        int id = productCategoryDao.save(productCategory);
        System.out.println(productCategory.getCategoryId());//getCategoryId获取插入数据的id
        System.out.println(id);//返回1代表插入成功
    }

    @Test
    public void find() throws Exception {
        List<Integer> types = new ArrayList<Integer>(){
            {
                add(2);
                add(3);
                add(4);
                add(5);
                add(6);
            }
        };

        List<ProductCategory> list = productCategoryDao.findByCategoryTypeIn(types);
        for(ProductCategory productCategory : list) {
            System.out.println(productCategory.toString());
        }
    }

}