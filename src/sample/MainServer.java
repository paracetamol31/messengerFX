package sample;


import sample.com.server.Server;

import java.io.IOException;

public class MainServer {
    public static void main(String[] args)  {
        Server server = new Server();
        server.start();
    }
}
