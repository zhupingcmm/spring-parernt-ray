package com.ocbc.spring.mvc.adapter;

import com.ocbc.spring.mvc.handler.SimpleControllerHandler;
import com.ocbc.spring.mvc.model.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SimpleControllerHandlerAdapter implements HandlerAdapter{
    @Override
    public boolean supports(Object handler) {
        return handler instanceof SimpleControllerHandler;
    }

    @Override
    public ModelAndView handleRequest(Object handler, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ((SimpleControllerHandler)handler).handleRequest(req, resp);
        return null;
    }
}
