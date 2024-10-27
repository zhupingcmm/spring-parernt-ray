package com.ocbc.spring.mvc.handler;

import com.ocbc.spring.mvc.model.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaveUserHandler implements SimpleControllerHandler{
    @Override
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=utf-8");
        resp.getWriter().println("SaveUserHandler...");
        return null;
    }
}
