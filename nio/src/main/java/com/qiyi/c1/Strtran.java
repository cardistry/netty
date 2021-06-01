package com.qiyi.c1;

import com.qiyi.utils.ByteBufferUtil;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Strtran {
    public static void main(String[] args) {
        String str1 = "hello";
        String str2 = "";

        ByteBuffer buffer = ByteBuffer.allocate(16);

        buffer.put(str1.getBytes());
        ByteBufferUtil.debugAll(buffer);

        buffer.flip();

        str2 = StandardCharsets.UTF_8.decode(buffer).toString();

        System.out.println(str2);
        ByteBufferUtil.debugAll(buffer);


    }
}
