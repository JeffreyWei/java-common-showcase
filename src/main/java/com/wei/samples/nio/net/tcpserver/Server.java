package com.wei.samples.nio.net.tcpserver;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by wei on 15/5/13.
 */
public class Server {
    private int port;
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    public Server(int port) {
        this.port = port;
        initServer();

    }

    private void initServer() {

    }

    public boolean listen() {
        boolean result = true;
        try {

        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    public Server() {
        this(8080);
    }

}
