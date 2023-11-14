package io.github.elihuso;

import com.sun.net.httpserver.HttpServer;
import io.github.elihuso.handler.GetBookHandler;
import io.github.elihuso.handler.GetManageHandler;
import io.github.elihuso.handler.GetUserHandler;

import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8086), 0);
        server.createContext("/data/user.json", new GetUserHandler());
        server.createContext("/data/book.json", new GetBookHandler());
        server.createContext("/data/admin.json", new GetManageHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}