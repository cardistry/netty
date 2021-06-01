package com.qiyi.selector.server;

import com.qiyi.utils.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SelectServer {
    public static void main(String[] args) {
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.bind(new InetSocketAddress(8080));

            Selector selector = Selector.open();
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT,null);

            System.out.println("server start ...");
            List<SocketChannel> channels = new ArrayList<>();
            ByteBuffer buffer = ByteBuffer.allocate(16);
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

                        iter.remove();

                    }
                }

            }


        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
