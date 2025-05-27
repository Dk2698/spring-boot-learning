package com.kumar.springbootlearning.singleton.context;

import java.util.HashMap;
import java.util.Map;

public class SimpleExecutionContext {

    private static final ThreadLocal<Map<String, Object>> executionContext = new ThreadLocal<>();

    public static Object getAttribute(String key) {
        final Map<String, Object> objectMap = executionContext.get();
        if (objectMap != null) {
            return objectMap.get(key);
        } else {
            return null;
        }
    }

    public static void setAttribute(String key, Object value) {
        Map<String, Object> objectMap = executionContext.get();
        if (objectMap == null) {
            objectMap = new HashMap<>();
            executionContext.set(objectMap);
        }
        objectMap.put(key, value);
    }

    public static void clear() {
        executionContext.remove();
    }
}