package com.kumar.springbootlearning.utils;

public final class ClassUtils {
    private ClassUtils() {
    }

    public static <T> Class<? extends T> safeCast(Class<?> raw , Class<T> baseClass)  {
        if (!baseClass.isAssignableFrom(raw)) {
            throw new IllegalArgumentException("Class " + raw.getName() + " does not extend " + baseClass.getName());
        }
        @SuppressWarnings("unchecked")
        Class<? extends T> typed = (Class<? extends T>) raw;
        return typed;
    }
}