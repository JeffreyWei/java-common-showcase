package com.wei.samples.nio.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by wei on 15/5/16.
 */
public class TCPServer implements Runnable {
    private Selector selector;
    private ServerSocketChannel ssc;

    public TCPServer(int port) throws IOException {
        ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(port));
        selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
    }

    /**
     * 停止服务器端
     */
    public void stopServer() {
        try {
            if (selector != null && selector.isOpen()) {
                selector.close();
            }
            if (ssc != null && ssc.isOpen()) {
                ssc.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                int selectSize = selector.select();
                if (selectSize <= 0) continue;
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if (key.isAcceptable()) {
                        doAcceptable(key);
                    }
                    if (key.isWritable()) {
                        doWriteMessage(key);
                    }
                    if (key.isReadable()) {
                        doReadMessage(key);
                    }
                    if (key.isConnectable()) {
                        doConnectable(key);
                    }
                    iter.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void doConnectable(SelectionKey key) {
        System.out.println("is connectalbe");
    }

    private void doReadMessage(SelectionKey key) throws IOException {
        System.out.println("is readable");
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer bb = ByteBuffer.allocate(8);
        System.out.println("receive from clint:");
        int read = sc.read(bb);
        while (read > 0) {
            bb.flip();
            byte[] barr = new byte[bb.limit()];
            bb.get(barr);
            System.out.print(new String(barr, "UTF-8"));
            bb.clear();
            read = sc.read(bb);
        }
        System.out.println("");
    }

    private void doWriteMessage(SelectionKey key) throws IOException {
        System.out.println("is writable");
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.wrap("how are you?".getBytes("UTF-8"));
        while (buffer.hasRemaining()) {
            sc.write(buffer);
        }
    }

    private void doAcceptable(SelectionKey key) throws IOException {
        System.out.println("is acceptable");
        ServerSocketChannel tempSsc = (ServerSocketChannel) key.channel();
        SocketChannel ss = tempSsc.accept();
        ss.configureBlocking(false);
        ss.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
    }
}
