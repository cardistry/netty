package com.qiyi.selector.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Client {
    public static void main(String[] args) {
        try (SocketChannel sc = SocketChannel.open()) {
            sc.connect(new InetSocketAddress("localhost",8080));
            System.out.println("connect waiting...");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
