package com.qiyi.selector.server;

import com.qiyi.utils.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ReadServer {
    public static void main(String[] args) {
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.bind(new InetSocketAddress(8080));

            Selector selector = Selector.open();
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT,null);

            System.out.println("server start ...");
            List<SocketChannel> channels = new ArrayList<>();
            while (true){
                // 若没有事件就绪，线程会被阻塞，反之不会被阻塞。从而避免了CPU空转
                // 返回值为就绪的事件个数

                int ready = selector.select();
                System.out.println("selector ready counts : " + ready);

                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                Iterator<SelectionKey> iter = selectionKeys.iterator();

                while (iter.hasNext()){
                    SelectionKey selectionKey = iter.next();

                    if(selectionKey.isAcceptable()){
                        ServerSocketChannel sschannel = (ServerSocketChannel) selectionKey.channel();

                        System.out.println("before accept...");

                        SocketChannel schannel = sschannel.accept();

                        System.out.println("after accept...");

                        schannel.configureBlocking(false);

                        ByteBuffer buffer = ByteBuffer.allocate(16);

                        schannel.register(selector,SelectionKey.OP_READ,buffer);

                        iter.remove();

                    }else if(selectionKey.isReadable()){
                        SocketChannel schannel = (SocketChannel) selectionKey.channel();

                        System.out.println("before reading... ");

                        ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();

                        try {
                            if(-1 == schannel.read(buffer)){
                                //客户端正常退出
                                System.out.println("客户端正常退出 ...");
                                selectionKey.cancel();
                            }else {
                                split(buffer);

                                if(buffer.position() == buffer.limit()){
                                    ByteBuffer newbuffer = ByteBuffer.allocate(buffer.capacity() * 2);

                                    buffer.flip();

                                    newbuffer.put(buffer);

                                    selectionKey.attach(newbuffer);
                                }

                                System.out.println("after reading ...");

                            }
                            iter.remove();
                        }catch (IOException e){
                            //客户端异常退出
                            System.out.println("客户端异常退出");
                            selectionKey.cancel();
                        }

                    }
                }

            }


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void split(ByteBuffer buffer) {
        buffer.flip();
        for(int i = 0; i < buffer.limit(); i++) {
            // 遍历寻找分隔符
            // get(i)不会移动position
            if (buffer.get(i) == '\n') {
                // 缓冲区长度
                int length = i+1-buffer.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                // 将前面的内容写入target缓冲区
                for(int j = 0; j < length; j++) {
                    // 将buffer中的数据写入target中
                    target.put(buffer.get());
                }
                // 打印结果
                ByteBufferUtil.debugAll(target);
            }
        }
        // 切换为写模式，但是缓冲区可能未读完，这里需要使用compact
        buffer.compact();
    }
}
