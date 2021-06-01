package com.qiyi.c1;

import com.qiyi.utils.ByteBufferUtil;

import java.nio.ByteBuffer;

public class BufferTest {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put((byte)97);
        ByteBufferUtil.debugAll(buffer);

        buffer.put(new byte[]{98,99,100,101});
        ByteBufferUtil.debugAll(buffer);



        //切换模式
        buffer.flip();
        ByteBufferUtil.debugAll(buffer);

        System.out.println(buffer.get());
        System.out.println(buffer.get());

        ByteBufferUtil.debugAll(buffer);

        //压缩
        buffer.compact();
        ByteBufferUtil.debugAll(buffer);
        buffer.put((byte)102);
        buffer.put((byte)103);
        ByteBufferUtil.debugAll(buffer);

    }
}
