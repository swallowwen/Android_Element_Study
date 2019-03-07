package com.example.study.fifth_pass_network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by swallow on 2019/1/5.
 */

public class EchoServer {
    private ServerSocket serverSocket;

    public EchoServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            Socket client = serverSocket.accept();
            handleClient(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket client) {

    }


    public static void main(String[] argv) {
        EchoServer server = new EchoServer(9877);
        server.run();
    }
}
