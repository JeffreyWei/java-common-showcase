package com.wei.samples.nio.tcp;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by wei on 15/5/17.
 */
public class TCPServerClientTest {

    @Test(expected= IOException.class)
    public void testServer() throws Exception {
        TCPServer server = new TCPServer(8080);
        try {
            new Thread(server).start();
        } catch (Exception e) {
            e.printStackTrace();
            server.stopServer();
        }
    }

    @Test(expected= IOException.class)
    public void testClient() throws Exception {
        TCPClient client = new TCPClient(8080);
        try {
            new Thread(client).start();
        } catch (Exception e) {
            e.printStackTrace();
            client.stopServer();
        }
    }
}