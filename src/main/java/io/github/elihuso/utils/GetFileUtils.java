package io.github.elihuso.utils;

import java.io.File;
import java.nio.file.Files;

public class GetFileUtils {
    public static String getFile(String path) {
        String response = "";
        File file = new File(path);
        if (!file.exists()) {
            response = "[{\"title\":\"Failed to Read File.\",\"description\":\"\",\"button1\":\"\",\"button2\":\"\"}]";
            return response;
        }
        try {
            response = new String(Files.readAllBytes(file.toPath()));
        }
        catch (Exception ex) {
            String title = ex.getMessage();
            String description = ex.toString();
            response = "\"title\":\"" + title + "\",description:\"" + description + "\",\"button1\":\"\",\"button2\":\"\"}]";
        }
        return response;
    }
}
