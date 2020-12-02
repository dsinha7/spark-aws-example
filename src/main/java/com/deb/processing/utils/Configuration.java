package com.deb.processing.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

public class Configuration {

    private Properties config;
    public Configuration(String configFile) throws IOException{

        Path configPath = Paths.get(configFile);
        System.out.println("Config location: " + configPath.toString());
        config = new Properties();
        try(InputStream stream = Files.newInputStream(configPath)){
            config.load(stream);
        }

    }

    public String getConfigValue(String key) {
        return config.getProperty(key);
    }
}
