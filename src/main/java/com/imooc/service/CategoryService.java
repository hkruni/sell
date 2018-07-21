package com.imooc.service;

import com.imooc.dao.ProductCategoryDao;
import com.imooc.dataobject.ProductCategory;
import com.imooc.vo.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;


    public ProductCategory findOne(Integer categoryId) {
        return productCategoryDao.findOne(categoryId);
    }

    //@Cacheable(value = "category" ,keyGenerator="wiselyKeyGenerator")
    public List<ProductCategory> findAll(Query query) {

        //HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //System.out.println("service层获取参数： " + req.getParameter("name"));;
        return productCategoryDao.findAll(query);
    }

    public List<ProductCategory> findAllWithProduct() {
        return productCategoryDao.findAllWithProduct();
    }

    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return productCategoryDao.findByCategoryTypeIn(categoryTypeList);
    }

    public ProductCategory save(ProductCategory productCategory) {
        productCategoryDao.save(productCategory);
        return productCategoryDao.findOne(productCategory.getCategoryId());

    }
}
