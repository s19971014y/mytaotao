package com.taotao.result;

import com.taotao.utils.JedisUtils;

public class MyTest {
    public static void main(String[] args) {
        JedisUtils.set("key1","huaji");

        System.out.println( JedisUtils.get("key1"));
    }
}
