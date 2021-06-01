package com.qiyi.c1;

import com.qiyi.utils.ByteBufferUtil;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Strtranv2 {
    public static void main(String[] args) {
        String str1 = "hello";
        String str2 = "";

        //初始为读模式
        //ByteBuffer buffer = StandardCharsets.UTF_8.encode(str1);
        ByteBuffer buffer = ByteBuffer.wrap(str1.getBytes(StandardCharsets.UTF_8));
        ByteBufferUtil.debugAll(buffer);



        str2 = StandardCharsets.UTF_8.decode(buffer).toString();

        System.out.println(str2);
        ByteBufferUtil.debugAll(buffer);
    }
}
