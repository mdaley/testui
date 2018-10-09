package com.sequsoft.testui.utils;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public static <T> T loadYaml(InputStream source, Class<T> clazz) {
        Yaml yaml = new Yaml(new Constructor(clazz));
        return yaml.load(source);
    }

    public static <T> T loadYaml(String resourcePath, Class<T> clazz) {
        try {
            try (InputStream stream = Utils.class.getResourceAsStream(resourcePath)) {
                return loadYaml(stream, clazz);
            }
        } catch (IOException e) {
            throw new RuntimeException(String.format("Couldn't open yaml resource %s.", resourcePath), e);
        }
    }
}
