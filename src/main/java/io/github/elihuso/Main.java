package io.github.elihuso;

import com.sun.net.httpserver.HttpServer;
import io.github.elihuso.handler.GetUserHandler;

import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8086), 0);
        server.createContext("/data/user.json", new GetUserHandler());
        server.createContext("/data/book.json", new GetUserHandler());
        server.createContext("/data/admin.json", new GetUserHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}