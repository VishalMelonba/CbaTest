package com.petstore.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtils {
    private static ConfigUtils instance;
    private final Properties properties = new Properties();

    public ConfigUtils() {
        try (FileInputStream input = new FileInputStream("src/test/resources/configs/config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized ConfigUtils getInstance() {
        if (instance == null) {
            instance = new ConfigUtils();
        }
        return instance;
    }

    // Get the BASE_URL from the config file
    public String getBaseUrl() {
        return properties.getProperty("api.base.url");
    }


    public String buildApiUrl(String endpoint) {
        return getBaseUrl() + "/pet" + endpoint;
    }

    public String getStatusFromConfig() {
        return properties.getProperty("status");
    }

}
