package com.cscb634.ejournal.util.spring;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Utils {

    public static <T> void mergeObjects(T source, T target) {
        List<Field> fields = getAllFields(target.getClass());
        try {
            for (Field field : fields) {
                // ignore synthetic methods, which are added at runtime by jacoco (or other libraries)
                if (field.isSynthetic()) {
                    continue;
                }
                boolean accessible = field.canAccess(target);
                field.setAccessible(true);
                Object value1 = field.get(target);
                Object value2 = field.get(source);
                Object value = isEmpty(value1) ? value2 : value1;
                field.set(target, value);
                field.setAccessible(accessible);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static boolean isEmpty(Object object) {
        if (object instanceof Collection) {
            return ((Collection) object).isEmpty();
        }
        if (object instanceof Map) {
            return ((Map) object).isEmpty();
        }
        return object == null;
    }

    private static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();

        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }

        return fields;
    }

}
