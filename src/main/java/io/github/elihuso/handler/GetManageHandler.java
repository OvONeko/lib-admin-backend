package io.github.elihuso.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class GetManageHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        File file = new File("./json/admin.json");
        if (!file.exists()) {
            String response = "[{\"title\":\"Failed to Read File.\",\"description\":\"\",\"button1\":\"\",\"button2\":\"\"}]";
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
            return;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            StringBuilder stringBuilder = new StringBuilder();
            while (fileInputStream.read(buffer) != -1) {
                stringBuilder.append(buffer);
                buffer = new byte[1024];
            }
            String response = stringBuilder.toString();
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        }
        catch (Exception ex) {
            String title = ex.getMessage();
            String description = ex.toString();
            String response = "\"title\":\"" + title + "\",description:\"" + description + "\",\"button1\":\"\",\"button2\":\"\"}]";
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        }
    }
}
