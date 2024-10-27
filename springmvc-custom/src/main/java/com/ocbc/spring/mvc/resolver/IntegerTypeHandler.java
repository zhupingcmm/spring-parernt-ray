package com.ocbc.spring.mvc.resolver;

public class IntegerTypeHandler implements TypeHandler{
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == Integer.class;
    }

    @Override
    public Object resolve(String[] args) {
        if (args == null || args.length == 0) return null;

        return Integer.parseInt(args[0]);
    }
}
