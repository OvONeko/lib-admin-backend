package io.github.elihuso;

import com.sun.net.httpserver.HttpServer;
import io.github.elihuso.handler.GetBookHandler;
import io.github.elihuso.handler.GetManageHandler;
import io.github.elihuso.handler.GetUserHandler;

import java.io.File;
import java.net.InetSocketAddress;

public class Main {
    private static final File[] files = {
            new File("./json/user.json"),
            new File("./json/book.json"),
            new File("./json/admin.json")
    };

    public static void main(String[] args) throws Exception {
        for (var v : files) {
            if (!v.exists()) {
                System.out.println(v.getPath() + " not found. Aborting...");
                return;
            }
        }
        HttpServer server = HttpServer.create(new InetSocketAddress(8086), 0);
        server.createContext("/data/user.json", new GetUserHandler());
        server.createContext("/data/book.json", new GetBookHandler());
        server.createContext("/data/admin.json", new GetManageHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        while (!(Character.toLowerCase(System.in.read()) == 'q')) ;
        server.stop(0);
    }
}