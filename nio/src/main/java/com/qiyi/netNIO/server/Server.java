package com.qiyi.netNIO.server;

import com.qiyi.utils.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.bind(new InetSocketAddress(8080));
            List<SocketChannel> channels = new ArrayList<>();
            ByteBuffer buffer = ByteBuffer.allocate(16);
            while (true){
                System.out.println("before connnect ...");
                // 设置为非阻塞模式，没有连接时返回null，不会阻塞线程
                ssc.configureBlocking(false);
                SocketChannel sc =  ssc.accept();
                // 通道不为空时才将连接放入到集合中
                if (sc != null) {
                    System.out.println("after connecting...");
                    channels.add(sc);
                }

                for(SocketChannel channel: channels){
                    System.out.println("before reading ...");
                    //通道中没有数据会阻塞
                    // 处理通道中的数据
                    // 设置为非阻塞模式，若通道中没有数据，会返回0，不会阻塞线程
                    channel.configureBlocking(false);
                    int read =  channel.read(buffer);
                    if(read > 0){
                        buffer.flip();
                        ByteBufferUtil.debugRead(buffer);
                        buffer.clear();
                        System.out.println("after reading ...");
                    }

                }

            }


        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
