package com.qiyi.c1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class FileTest {

    public static void main(String[] args) {
        //readFile("test");
        //writeFile("test");
        transferTest("test","test1");

    }

    private static void readFile(String path){
        try (FileChannel fc = new FileInputStream(path).getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(10);

            StringBuilder s = new StringBuilder();
            while (fc.read(buffer) > 0){
                buffer.flip();
                while (buffer.hasRemaining()){
                    s.append((char) buffer.get());
                }
                //清空
                buffer.clear();
            }
            System.out.println(s.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(String path){
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 102; i++) {
            s.append("123456789\n");
        }
        s.append("\n\n\n\n");
        try (FileChannel fc = new FileOutputStream(path).getChannel()){
            fc.position(0);
            //ByteBufferUtil.debugAll(buffer);
            for (int i = 0; i < 4 * 1024 * 1024; i++) {
                ByteBuffer buffer = ByteBuffer.wrap(s.toString().getBytes(StandardCharsets.UTF_8));
                while (buffer.hasRemaining()){
                    fc.write(buffer);
                }
            }
            System.out.println(fc.position());

        }catch (IOException e){
            e.printStackTrace();
        }

    }


    public  static void transferTest(String from,String to){
        try (FileChannel fcin = new FileInputStream(from).getChannel();
            FileChannel fcout = new FileOutputStream(to).getChannel();) {
            long size = fcin.size();
            long capacity = fcin.size();
            while (capacity > 0){
                capacity -= fcin.transferTo(size - capacity,capacity,fcout);
                System.out.println("复制了一次" + capacity);
            }
            System.out.println(fcout.size());
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
