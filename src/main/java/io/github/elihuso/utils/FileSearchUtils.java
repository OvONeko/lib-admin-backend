package io.github.elihuso.utils;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

public class FileSearchUtils {
    public static String Search(String path, String value) {
        String content = GetFileUtils.getFile(path);
        JsonReader reader = Json.createReader(new StringReader(content));
        JsonArray data = reader.readArray();
        String response = "[";
        for (int i = 0; i < data.size(); ++i) {
            JsonObject item = data.getJsonObject(i);
            if ((item.getString("title").contains(value)) || (item.getString("description").contains(value))) {
                if (!(response.equalsIgnoreCase("[")))
                    response += ",";
                response += item.toString();
            }
        }
        response += "]";
        return response;
    }
}
