package io.github.elihuso.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class GetUserHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        File file = new File("./json/user.json");
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
