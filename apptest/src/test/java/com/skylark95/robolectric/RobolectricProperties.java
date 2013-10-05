package com.skylark95.robolectric;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class RobolectricProperties {
    
    private static final Properties PROPERTIES = load("robolectric.properties");
    
    private RobolectricProperties() {
    }
    
    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static Properties load(String name) {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(name));
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
}
