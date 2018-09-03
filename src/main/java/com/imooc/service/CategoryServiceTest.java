package com.imooc.service;

import com.imooc.dataobject.ProductCategory;
import com.imooc.vo.Query;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by hukai on 2018/8/19.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @org.junit.Test
    public void testFindOne() throws Exception {
        System.out.println("11111");
    }

    @org.junit.Test
    public void testFindAll() throws Exception {
        Query query = new Query();
        query.setCategoryId(1);
        List<ProductCategory>  list = categoryService.findAll(query);
        System.out.println(list.size());
    }

    @org.junit.Test
    public void testFindAllWithProduct() throws Exception {

    }

    @org.junit.Test
    public void testFindByCategoryTypeIn() throws Exception {

    }

    @org.junit.Test
    public void testSave() throws Exception {

    }
}