package com.ocbc.spring.mvc.resolver;

public class StringTypeHandler implements TypeHandler{
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == String.class;
    }

    @Override
    public Object resolve(String[] args) {
        if(args == null || args.length == 0){
            return null;
        }
        return args[0];
    }
}
