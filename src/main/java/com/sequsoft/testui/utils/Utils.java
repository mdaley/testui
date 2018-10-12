package com.sequsoft.testui.utils;

import org.apache.commons.text.CaseUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public static <T> T loadYaml(InputStream source, Class<T> clazz) {
        Constructor con = new Constructor(clazz);

        con.setPropertyUtils(new PropertyUtils() {

            @Override
            public Property getProperty(Class<?> type, String name) {
                if (name.indexOf("-") > -1) {
                    name = CaseUtils.toCamelCase(name, false, new char[] {'-'});
                }
                return super.getProperty(type, name);
            }
        });

        Yaml yaml = new Yaml(con);
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
