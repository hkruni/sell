package com.imooc.dataobject;

import lombok.Data;

import java.util.Date;

/**
 * Created by hukai on 2018/9/23.
 */
@Data
public class Book {

    private String  id;

    private Integer word_count;

    private String author;

    private String title;

    private Date publish_date;

    public Book(String id,Integer word_count, String author, String title, Date publish_date) {
        this.id = id;
        this.word_count = word_count;
        this.author = author;
        this.title = title;
        this.publish_date = publish_date;
    }
}
