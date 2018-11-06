package com.imooc.service;

import com.google.common.collect.Lists;
import com.imooc.dao.ProductCategoryDao;
import com.imooc.dao.ProductInfoDao;
import com.imooc.dataobject.ProductCategory;
import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.CartDTO;
import com.imooc.dto.Product;
import com.imooc.enums.ProductStatusEnum;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 廖师兄
 * 2017-05-09 17:31
 */
@Service
public class ProductService {

    @Autowired
    private ProductInfoDao productInfoDao;

    @Autowired
    private ProductCategoryDao productCategoryDao;

    public ProductInfo findOne(String productId) {
        return productInfoDao.findOne(productId);
    }

    public List<ProductInfo> findUpAll() {
        return productInfoDao.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    public List<ProductInfo> findAll() {
        List<ProductInfo>  list =  productInfoDao.findAll();
        Map<Integer,List<ProductInfo>> map = new HashMap<>();
        for (ProductInfo productInfo : list) {
            Integer categoryType = productInfo.getCategoryType();
            if (map.get(categoryType) == null) {
                List<ProductInfo> productInfoList = Lists.newArrayList(productInfo);
                map.put(categoryType,productInfoList);
            } else {
                map.get(categoryType).add(productInfo);
            }
        }

        for (Map.Entry<Integer, List<ProductInfo>> integerListEntry : map.entrySet()) {
            System.out.println(integerListEntry.getKey() + "--------------");
            integerListEntry.getValue().stream().forEach(System.out::println);
        }

        return list;

    }

    public ProductInfo save(ProductInfo productInfo) {
        productInfoDao.save(productInfo);
        return  productInfoDao.findOne(productInfo.getProductId());
    }



    public void increaseCatagoryAndProduct(ProductCategory productCategory) {
        try {
            int x = productCategoryDao.save(productCategory);
            System.out.println("increaseCatagoryAndProduct : " + x);
        }catch (Exception e) {
            System.out.println("1111111111111111");
            //e.printStackTrace();
        }


        //int a = 1 / 0;

        //productInfoDao.save(productInfo);
    }


    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO: cartDTOList) {
            ProductInfo productInfo = productInfoDao.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);

            productInfoDao.save(productInfo);
        }

    }

    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO: cartDTOList) {
            ProductInfo productInfo = productInfoDao.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);

            productInfoDao.save(productInfo);
        }
    }

    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = productInfoDao.findOne(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfoDao.save(productInfo);
        return productInfoDao.findOne(productId);
    }

    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = productInfoDao.findOne(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfoDao.save(productInfo);
        return productInfoDao.findOne(productId);
    }


    /**
     * 测试动态修改字段
     * @return
     */
    public int updateProduct(){

        Map<String ,Object> map = new HashMap<String ,Object>();
        map.put("category_name","'测试1'");
        map.put("category_type",2);

        return productCategoryDao.updateProduct(10,map);

    }



}
