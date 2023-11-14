package io.github.elihuso.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;

public class GetUserHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        File file = new File("./json/user.json");
        httpExchange.getResponseHeaders().add("Content-Type", "application/json");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Headers","origin, content-type, accept, authorization");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        if (!file.exists()) {
            String response = "[{\"title\":\"Failed to Read File.\",\"description\":\"\",\"button1\":\"\",\"button2\":\"\"}]";
            httpExchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
            return;
        }
        try {
            String response = new String(Files.readAllBytes(file.toPath()));
            httpExchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        }
        catch (Exception ex) {
            String title = ex.getMessage();
            String description = ex.toString();
            String response = "\"title\":\"" + title + "\",description:\"" + description + "\",\"button1\":\"\",\"button2\":\"\"}]";
            httpExchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        }
    }
}
