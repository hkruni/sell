package com.imooc.dataobject;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by 廖师兄
 * 2017-06-11 17:20
 */
@Data
public class OrderDetail {

    private String detailId;

    /** 订单id. */
    private String orderId;

    /** 商品id. */
    private String productId;

    /** 商品名称. */
    private String productName;

    /** 商品单价. */
    private BigDecimal productPrice;

    /** 商品数量. */
    private Integer productQuantity;

    /** 商品小图. */
    private String productIcon;
}
