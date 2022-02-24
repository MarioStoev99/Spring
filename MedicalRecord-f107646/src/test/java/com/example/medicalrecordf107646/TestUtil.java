package com.example.medicalrecordf107646;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class TestUtil {

    public static String loadResource(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {

            fail(e.getMessage());
            return null;
        }
    }

    public static <T> T deserialize(String json, Class<T> clazz) {
        return deserialize(json, clazz, null);
    }

    public static <T> T deserialize(String json, Class<T> clazz, JsonDeserializer<T> jsonDeserializer) {
        T obj = null;
        try {
            obj = configureDeserializer(clazz, jsonDeserializer)
                    .readerFor(clazz)
                    .readValue(json);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Provided json cannot be deserialized in object from type " + clazz
                    .getSimpleName());
        }
        return obj;
    }

    private static <T> ObjectMapper configureDeserializer(Class<T> clazz, JsonDeserializer<T> jsonDeserializer) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.findAndRegisterModules();

        if (jsonDeserializer == null) {
            return objectMapper;
        }

        SimpleModule module = new SimpleModule();
        module.addDeserializer(clazz, jsonDeserializer);

        objectMapper.registerModule(module);

        return objectMapper;
    }
}
