package com.wei.samples.nio.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by wei on 15/5/16.
 */
public class TCPClient implements Runnable {
    private SocketChannel socketChannel;
    private Selector selector;

    public TCPClient(int port) throws IOException {
        socketChannel = SocketChannel.open();
        selector = Selector.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        socketChannel.connect(new InetSocketAddress(port));
        while (!socketChannel.finishConnect()) {
            System.out.println("check finish connection");
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {
        TCPClient client = new TCPClient(8080);
        try {
            new Thread(client).start();
        } catch (Exception e) {
            e.printStackTrace();
            client.stopServer();
        }
    }

    public void run() {
        while (true) {
            try {
                writeMessage();
                int select = selector.select();
                if (select > 0) {
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = keys.iterator();
                    while (iter.hasNext()) {
                        SelectionKey sk = iter.next();
                        if (sk.isReadable()) {
                            readMessage(sk);
                        }
                        iter.remove();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void readMessage(SelectionKey sk) throws IOException {
        SocketChannel curSc = (SocketChannel) sk.channel();
        ByteBuffer buffer = ByteBuffer.allocate(8);
        while (curSc.read(buffer) > 0) {
            buffer.flip();
            System.out.println("Receive from server:"
                    + new String(buffer.array(), "UTF-8"));
            buffer.clear();
        }
    }

    public void writeMessage() throws IOException {
        try {
            String msg = "Server,how are you?";
            ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes("UTF-8"));
            while (buffer.hasRemaining()) {
                System.out.println("buffer.hasRemaining() is true.");
                socketChannel.write(buffer);
            }
        } catch (IOException e) {
            if (socketChannel.isOpen()) {
                socketChannel.close();
            }
            e.printStackTrace();
        }
    }

    /**
     * 停止客户端
     */
    private void stopServer() {
        try {
            if (selector != null && selector.isOpen()) {
                selector.close();
            }
            if (socketChannel != null && socketChannel.isOpen()) {
                socketChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
