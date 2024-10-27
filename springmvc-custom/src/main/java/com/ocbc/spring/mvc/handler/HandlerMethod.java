package com.ocbc.spring.mvc.handler;

import java.lang.reflect.Method;

public class HandlerMethod {

    private Object handler;

    private Method method;

    public HandlerMethod(Object handler, Method method) {
        this.handler = handler;
        this.method = method;
    }

    public Object getHandler() {
        return handler;
    }

    public Method getMethod() {
        return method;
    }
}
