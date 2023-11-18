package io.github.elihuso.config;

import com.github.jsixface.YamlConfig;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class ConfigManager {

    private YamlConfig config = null;

    public ConfigManager() throws Exception {
        if (!CheckConfigExist().equals(ExistStatus.EXIST))
            if (!CreateConfig())
                throw new Exception("Failed to create default config");
        this.config = YamlConfig.load(new StringReader(GetFileConfigString()));
    }

    private ExistStatus CheckConfigExist() {
        File directory = new File("./config");
        File file = new File("./config/config.yaml");
        try {
            if (!directory.exists())
                return ExistStatus.NO_DIRECTORY;
            if (!file.exists())
                return ExistStatus.NOT_EXIST;
        }
        catch (Exception ex) {
            return ExistStatus.OTHER_ERROR;
        }
        return ExistStatus.EXIST;
    }

    private boolean CreateConfig() {
        try {
            ExistStatus status = CheckConfigExist();
            if (status.equals(ExistStatus.EXIST))
                return false;
            if (status.equals(ExistStatus.NOT_EXIST)) {
                File file = new File("./config/config.yaml");
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.write(GetDefaultConfigString());
            }
            if (status.equals(ExistStatus.NO_DIRECTORY)) {
                File dir = new File("./config");
                File file = new File("./config/config.yaml");
                dir.mkdirs();
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.write(GetDefaultConfigString());
            }
            if (status.equals(ExistStatus.OTHER_ERROR)) {
                return false;
            }
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }

    private String GetDefaultConfigString() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.yaml");
        Scanner scanner = new Scanner(inputStream);
        return scanner.next();
    }

    @org.jetbrains.annotations.NotNull
    @org.jetbrains.annotations.Contract(" -> new")
    private String GetFileConfigString() throws IOException {
        return new String(Files.readAllBytes(Path.of("./config/config.yaml")));
    }

    public YamlConfig getConfig() {
        return this.config;
    }

    public String getString(String key) {
        return this.config.getString(key);
    }

    public int getInt(String key) {
        return this.config.getInt(key);
    }

    public int getPort() {
        return this.getInt("port");
    }
}
