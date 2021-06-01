package com.qiyi.c1;

import com.qiyi.utils.ByteBufferUtil;

import java.nio.ByteBuffer;

public class ByteBufferDemo {

    public static void main(String[] args) {
        ByteBufferDemo bufferDemo = new ByteBufferDemo();
        ByteBuffer buffer = ByteBuffer.allocate(32);
        buffer.put("Hello,world\nI'm Nyima\nHo".getBytes());
        // 调用split函数处理
        bufferDemo.split(buffer);
        buffer.put("w are you?\n".getBytes());
        split(buffer);
    }



    public static void split(ByteBuffer buffer){

        buffer.flip();

        for (int i = 0; i < buffer.limit(); i++) {
            if(buffer.get(i) == '\n'){
                int len = i + 1 - buffer.position();
                ByteBuffer tmp = ByteBuffer.allocate(len);
                for (int j = 0; j < len; j++) {
                    tmp.put(buffer.get());
                }
                ByteBufferUtil.debugAll(tmp);
            }
        }
        buffer.compact();
    }

}
