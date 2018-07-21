package com.imooc.dao;

import com.imooc.dataobject.ProductCategory;
import com.imooc.dto.Product;
import com.imooc.vo.Query;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 廖师兄
 * 2017-05-07 14:35
 */
@Repository
public interface ProductCategoryDao {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory findOne(@Param("id") Integer id);

    List<ProductCategory> findAll(@Param("query") Query query);

    public int save(ProductCategory productCategory);

    List<ProductCategory> findAllWithProduct();

    public int updateProduct(@Param("id") Integer id,@Param("map") Map<String ,Object> map);

}
