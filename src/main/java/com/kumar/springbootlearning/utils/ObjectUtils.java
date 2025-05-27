package com.kumar.springbootlearning.utils;

import java.util.function.Supplier;

public final class ObjectUtils {

    private ObjectUtils() {
    }

    /**
     * if {@code value} is not null and is equal to {@code Boolean.TRUE} return {@code true} else return {@code false}
     *
     * @param value - value to evaluate
     * @return {@code true} if {@code value} is not null and is equal to {@code Boolean.TRUE} else {@code false}
     */
    public static boolean booleanValue(Boolean value) {
        return value != null && value;
    }

    /**
     * if {@code value} is not null return boolean value of {@code value} else return {@code defaultValue}
     *
     * @param value        - value to evaluate
     * @param defaultValue - default value to return if value is null
     * @return {@code value} is not null return boolean value of {@code value} else return {@code defaultValue}
     */
    public static boolean booleanValue(Boolean value, boolean defaultValue) {
        return value != null ? value : defaultValue;
    }

    /**
     * Can be used to safely evaluate chained evaluation of an expression such as getUser().getAddress.getCity();
     * <p>
     * usage - nullGuard(() -> getUser().getAddress.getCity(), "default city")
     *
     * @param supplier     - supplier which evaluates to desired expression
     * @param defaultValue - default value to return if the expression results in NullPointerExpression at intermediate steps.
     * @param <T>          -
     * @return evaluation result of the expression or defaultValue
     */
    public static <T> T nullGuard(Supplier<T> supplier, T defaultValue) {
        try {
            return supplier.get();
        } catch (@SuppressWarnings("unused") NullPointerException ignored) {
            return defaultValue;
        }
    }

    /**
     * Can be used to safely evaluate chained evaluation of an expression such as getUser().getAddress.getCity();
     * <p>
     * usage - nullGuard(() -> getUser().getAddress.getCity())
     *
     * @param supplier - supplier which evaluates to desired expression
     * @param <T>      -
     * @return evaluation result of the expression or null
     */
    public static <T> T nullGuard(Supplier<T> supplier) {
        return nullGuard(supplier, null);
    }

    /**
     * Safely casts an object to the specified target type.
     * <p>
     * This method checks whether the given object is an instance of the target class
     * before performing the cast. If it is not, an {@link IllegalArgumentException} is thrown.
     * This avoids unchecked cast warnings and ensures runtime type safety.
     *
     * @param object    the object to cast
     * @param baseClass the class to cast the object to
     * @param <T>       the target type
     * @return the object cast to the target type
     * @throws IllegalArgumentException if the object is not an instance of the target class
     */
    public static <T> T safeCast(Object object, Class<T> baseClass) {
        if (!baseClass.isInstance(object)) {
            throw new IllegalArgumentException(
                    "Object of type " + object.getClass().getName() +
                            " is not an instance of " + baseClass.getName()
            );
        }
        return baseClass.cast(object);
    }
}