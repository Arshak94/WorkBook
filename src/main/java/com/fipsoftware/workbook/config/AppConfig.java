package com.fipsoftware.workbook.config;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
public class AppConfig {


    public Map<String, String> getXMLFolderPath() {

        List<String> paths = new ArrayList<>();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
        Properties properties = new Properties();
        Map<String, String> map = null;
        if (inputStream != null) {
            try {
                properties.load(inputStream);
                map = new HashMap<>();
                Set<Object> keys = properties.keySet();
                for (Object key : keys) {
                    String k = (String) key;
                    map.put(k, properties.getProperty(k));
                }
            } catch (IOException e) {
                System.err.println("config.properties file not found in the given classpath");
            }
        }

        return map;
    }
}
