package com.kumar.springbootlearning.cache;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component("customKeyGenerator")
public class CustomKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        // ClassName::MethodName::param1-param2
        String key = target.getClass().getSimpleName() + "::" +
                method.getName() + "::" +
                Arrays.stream(params)
                        .map(String::valueOf)
                        .collect(Collectors.joining("-"));
        return key;
    }
}

//keys products::*
// get "products::ProductService::getProductById::123"
