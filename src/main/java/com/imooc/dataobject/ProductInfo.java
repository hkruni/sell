package com.imooc.dataobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imooc.enums.ProductStatusEnum;
import com.imooc.util.EnumUtil;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品
 * Created by 廖师兄
 * 2017-05-09 11:30
 */
@Data
public class ProductInfo implements Serializable{

    private static final long serialVersionUID = 97524226934221772L;
    private String productId;

    /** 名字. */
    private String productName;

    /** 单价. */
    private BigDecimal productPrice;

    /** 库存. */
    private Integer productStock;

    /** 描述. */
    private String productDescription;

    /** 小图. */
    private String productIcon;

    /** 状态, 0正常1下架. */
    //private Integer productStatus = ProductStatusEnum.UP.getCode();

    private Integer productStatus ;

    /** 类目编号. */
    private Integer categoryType;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum() {
        return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productStock=" + productStock +
                ", productDescription='" + productDescription + '\'' +
                ", productIcon='" + productIcon + '\'' +
                ", productStatus=" + productStatus +
                ", categoryType=" + categoryType +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
