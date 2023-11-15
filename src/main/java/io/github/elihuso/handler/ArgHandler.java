package io.github.elihuso.handler;

import io.github.elihuso.utils.FileSearchUtils;
import io.github.elihuso.utils.GetFileUtils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;
import java.io.StringReader;

public class ArgHandler {
    public static String HandlePOSTArgs(String path, String source) {
        JsonReader reader = Json.createReader(new StringReader(source));
        JsonObject content = reader.readObject();
        reader.close();
        if (content.getString("method").equalsIgnoreCase("search")) {
            return FileSearchUtils.Search(path, content.getString("value", ""));
        }
        return GetFileUtils.getFile(path);
    }
}
