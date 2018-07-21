package com.imooc.dao;

import com.imooc.dataobject.ProductInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductInfoDao {


    public ProductInfo findOne(@Param("productId") String productId);

    List<ProductInfo> findByProductStatus(@Param("productStatus") Integer productStatus);

    List<ProductInfo> findAll();

    public int save(ProductInfo productInfo);

    public int update(@Param("fields") Map<String,Object> fields,@Param("product_id") String product_id);

}
