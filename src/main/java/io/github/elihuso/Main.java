package io.github.elihuso;

import com.sun.net.httpserver.HttpServer;
import io.github.elihuso.config.ConfigManager;
import io.github.elihuso.handler.GetBookHandler;
import io.github.elihuso.handler.GetManageHandler;
import io.github.elihuso.handler.GetUserHandler;
import io.github.elihuso.handler.PostAddHandler;
import io.github.elihuso.logic.Route;

import java.io.File;
import java.net.InetSocketAddress;

public class Main {

    public static ConfigManager config;

    static {
        try {
            config = new ConfigManager();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final File[] files = {
            new File("./json/user.json"),
            new File("./json/book.json"),
            new File("./json/admin.json")
    };

    private static final Route[] route = {
            new Route("/data/user.json", new GetUserHandler()),
            new Route("/data/book.json", new GetBookHandler()),
            new Route("/data/admin.json", new GetManageHandler()),
            new Route("/handle/add.json", new PostAddHandler())
    };

    public static void main(String[] args) throws Exception {
        for (var v : files) {
            if (!v.exists()) {
                System.out.println(v.getPath() + " not found. Aborting...");
                return;
            }
        }
        HttpServer server = HttpServer.create(new InetSocketAddress(config.getPort()), 0);
        for (var v : route) {
            server.createContext(v.getPath(), v.getHandler());
        }
        server.setExecutor(null); // creates a default executor
        server.start();
        while (!(Character.toLowerCase(System.in.read()) == 'q')) ;
        server.stop(0);
    }
}