package com.ocbc.spring.mvc.adapter;

import com.ocbc.spring.mvc.model.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface HandlerAdapter {
    boolean supports(Object handler);
    ModelAndView handleRequest(Object handler, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
