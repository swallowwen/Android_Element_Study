package com.example.study.fifth_pass_network;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by swallow on 2019/1/5.
 */

public class EchoClient {
    private Socket mScocket;

    public EchoClient(String host,int port) {
        try {
            mScocket=new Socket(host,port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){

    }


    public static void main(String[] argv) {
        // 由于服务端运行在同一主机，这里我们使用 localhost
        EchoClient client = new EchoClient("localhost", 9877);
        client.run();
    }
}
