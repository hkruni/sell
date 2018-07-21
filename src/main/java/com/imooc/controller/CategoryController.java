package com.imooc.controller;


import com.imooc.aop.LoginRequired;
import com.imooc.dataobject.ProductCategory;
import com.imooc.service.CategoryService;
import com.imooc.util.ResultVOUtil;
import com.imooc.vo.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    //@LoginRequired
    @GetMapping("getAllCaterogy")
    public Object getAllCaterogy(Query query) {
        List<ProductCategory> list = categoryService.findAll(query);

        return ResultVOUtil.success(list);
    }

    @GetMapping("getAllCategoryWithProduct")
    public Object getAllCategoryWithProduct() {
        List<ProductCategory> list = categoryService.findAllWithProduct();
        return ResultVOUtil.success(list);
    }
}
