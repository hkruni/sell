package com.imooc.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * http请求返回的最外层对象
 * Created by 廖师兄
 * 2017-05-12 14:13
 */
@Data
public class ResultVO<T> {

    /** 错误码. */
    @JsonProperty("r_code")
    private Integer code;

    /** 提示信息. */
    @JsonProperty("r_message")
    private String msg;

    /** 具体内容. */
    @JsonProperty("r_data")
    private T data;
}
