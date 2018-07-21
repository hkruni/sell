package com.imooc.learn;

import java.util.Collections;
import java.util.List;

public class TestEmpty {

    public static void main(String[] args) {

        List<String> list = Collections.<String >emptyList();

        for(String s : list) {
            System.out.println(s);
        }
    }
}
