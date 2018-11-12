package com.imooc.controller;


import com.google.common.collect.Lists;
import com.imooc.aop.LoginRequired;
import com.imooc.dataobject.ProductCategory;
import com.imooc.service.CategoryService;
import com.imooc.service.ProductService;
import com.imooc.util.CSVReportUtils;
import com.imooc.util.DateTimeUtil;
import com.imooc.util.ResultVOUtil;
import com.imooc.vo.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CategoryController {


    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    private static List<String> title = Lists.newArrayList("目录ID","目录名称","目录类型","创建时间","更新时间");

    @GetMapping("getAllCategory")
    @ResponseBody
    public  Object getAllCaterogy(Query query) {

//        System.out.println(query.getCategoryId());
//        System.out.println(query.getCategoryName());
//        System.out.println(query.getCategoryType());
//        System.out.println(query.getCreateTime());
//        System.out.println(query.getUpdateTime());

        List<ProductCategory> list = categoryService.findAll(query);
        return ResultVOUtil.success(list);
    }

    @GetMapping("getAllCategoryWithProduct")
    @ResponseBody
    public synchronized Object getAllCategoryWithProduct() {
        List<ProductCategory> list = categoryService.findAllWithProduct(3);
        return ResultVOUtil.success(list);
    }

    //重复插入会幂等性
    @PostMapping("addCategory")
    @ResponseBody
    public Object addCategory(ProductCategory productCategory) {
        productService.increaseCatagoryAndProduct(productCategory);
        return ResultVOUtil.success();

    }

    @GetMapping("csv")
    public void downloadCsv(HttpServletRequest request,HttpServletResponse response,Query query) {

        List<List<String>> data = new ArrayList<List<String>>();
        List<String> line = null;

        List<ProductCategory> list = categoryService.findAll(query);
        for (ProductCategory productCategory : list) {
            line = new ArrayList<String>();
            line.add(String.valueOf(productCategory.getCategoryId()));
            line.add(productCategory.getCategoryName());
            line.add(String.valueOf(productCategory.getCategoryType()));
            line.add(DateTimeUtil.dateToStr(productCategory.getCreateTime(),"yyyy-MM-dd"));
            line.add(DateTimeUtil.dateToStr(productCategory.getUpdateTime(),"yyyy-MM-dd"));
            data.add(line);
        }
        String result = CSVReportUtils.genCSVReport(title,data);
        response.setContentType("application/vnd.ms-excel;charset=GBK");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" +
                    java.net.URLEncoder.encode("目录报表", "UTF-8") + ".csv");
            BufferedOutputStream os = null;
            os = new BufferedOutputStream(response.getOutputStream());
            os.write(result.getBytes("GBK"));
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




}
