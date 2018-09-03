package com.imooc.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class Query {

    @Getter @Setter private Integer name;

    @Getter @Setter private Integer categoryId;

    @Getter @Setter private String categoryName;

    @Getter @Setter private Integer categoryType;

    @Getter @Setter private Date createTime;

    @Getter @Setter private Date updateTime;

}
