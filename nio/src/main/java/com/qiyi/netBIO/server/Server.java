package com.qiyi.netBIO.server;

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
                //没有连接会阻塞
                SocketChannel sc =  ssc.accept();
                System.out.println("after connect ...");
                channels.add(sc);

                for(SocketChannel channel: channels){
                    System.out.println("before reading ...");
                    //通道中没有数据会阻塞
                    channel.read(buffer);
                    buffer.flip();
                    ByteBufferUtil.debugRead(buffer);
                    buffer.clear();
                    System.out.println("after reading ...");
                }

            }


        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
