package io.github.elihuso.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;
import java.io.*;

public class PostAddHandler implements HttpHandler {

    private final String[][] RouteList = {
            {"admin", "./json/admin.json"},
            {"book", "./json/book.json"},
            {"user", "./json/user,json"}
    };

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        if (!httpExchange.getRequestMethod().equalsIgnoreCase("POST")) {
            httpExchange.sendResponseHeaders(403, response.getBytes().length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
            return;
        }
        JsonParser parser = Json.createParser(httpExchange.getRequestBody());
        JsonObject content = parser.getObject();
        parser.close();
        try {
            String type = content.getString("type");
            String title = content.getString("title");
            String description = content.getString("description");
            for (var v : RouteList) {
                if (v[0].equalsIgnoreCase(type)) {
                    InputStream is = new FileInputStream(v[1]);
                    JsonParser p = Json.createParser(is);
                    JsonArray adata = p.getArray();
                    JsonObject object = adata.getJsonObject(0);
                    object.replace("title", Json.createValue(title));
                    object.replace("description", Json.createValue(description));
                    adata.add(object);
                    File file = new File(v[1]);
                    FileWriter writer = new FileWriter(file);
                    writer.write(adata.toString());
                    response = "{\"status\":\"success\"}";
                    httpExchange.sendResponseHeaders(200, response.getBytes().length);
                    OutputStream outputStream = httpExchange.getResponseBody();
                    outputStream.write(response.getBytes());
                    outputStream.close();
                }
            }
        }
        catch (Exception ex) {
            response = "{\"status\":\"error\",\"message\":\"" + ex.getMessage() + "\",\"details\":\"" + ex.toString() + "\"}";
            httpExchange.sendResponseHeaders(201, response.getBytes().length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        }
    }
}
