package com.ocbc.spring.mvc.maping;

import javax.servlet.http.HttpServletRequest;

public class SimpleUrlHandlerMapping implements HandlerMapping{
    @Override
    public Object getHandler(HttpServletRequest request) {
        return null;
    }
}
