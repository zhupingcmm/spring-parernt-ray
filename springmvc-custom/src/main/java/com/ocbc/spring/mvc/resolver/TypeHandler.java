package com.ocbc.spring.mvc.resolver;

public interface TypeHandler {
    boolean supports(Class<?> clazz);

    Object resolve(String[] args);
}
