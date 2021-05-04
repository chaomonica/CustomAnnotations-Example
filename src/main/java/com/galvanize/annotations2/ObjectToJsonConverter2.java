package com.galvanize.annotations2;


import com.galvanize.annotations.JsonElement;
import com.galvanize.annotations.JsonSerializationException;
import com.galvanize.annotations2.JsonSerializable2;
import com.galvanize.annotations2.JsonSerializationException2;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ObjectToJsonConverter2 {
    public String convertToJson(Object object) {
        try {
            checkIfSerializable(object);
            initializeObject(object);
            return getJsonString(object);
        } catch (Exception e) {
            throw new JsonSerializationException(e.getMessage());
        }
    }
    private void checkIfSerializable(Object object) {
        if (Objects.isNull(object)) {
            throw new JsonSerializationException2("The object to serialize is null");
        }
        Class<?> clazz = object.getClass();
        if (!clazz.isAnnotationPresent(JsonSerializable2.class)) {
            throw new JsonSerializationException2("The class "
                    + clazz.getSimpleName()
                    + " is not annotated with JsonSerializable");
        }
    }

    private void initializeObject(Object object) throws Exception {
        Class<?> clazz = object.getClass();
        for (Method method: clazz.getDeclaredMethods()){
            if (method.isAnnotationPresent(Init2.class)) {
                method.setAccessible(true);
                method.invoke(object);
            }
        }
    }

    private String getJsonString(Object object) throws Exception {
        Class<?> clazz = object.getClass();
        Map<String, String> jsonElementsMap = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(JsonElement.class)) {
                jsonElementsMap.put(getKey(field), (String) field.get(object));
            }
        }

        String jsonString = jsonElementsMap.entrySet()
                .stream()
                .map(entry -> "\"" + entry.getKey() + "\":\""
                        + entry.getValue() + "\"")
                .collect(Collectors.joining(","));
        return "{" + jsonString + "}";
    }

    private String getKey(Field field) {
        String value = field.getAnnotation(JsonElement.class)
                .key();
        return value.isEmpty() ? field.getName() : value;
    }

}
