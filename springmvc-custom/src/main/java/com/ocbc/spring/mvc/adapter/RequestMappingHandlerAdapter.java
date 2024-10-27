package com.ocbc.spring.mvc.adapter;

import com.ocbc.spring.mvc.annotation.ResponseBody;
import com.ocbc.spring.mvc.handler.HandlerMethod;
import com.ocbc.spring.mvc.model.ModelAndView;
import com.ocbc.spring.mvc.resolver.IntegerTypeHandler;
import com.ocbc.spring.mvc.resolver.StringTypeHandler;
import com.ocbc.spring.mvc.resolver.TypeHandler;
import com.ocbc.spring.mvc.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestMappingHandlerAdapter implements HandlerAdapter{
    private final List<TypeHandler> typeHandlers = new ArrayList<>();


    public RequestMappingHandlerAdapter() {
        this.typeHandlers.add(new StringTypeHandler());
        this.typeHandlers.add(new IntegerTypeHandler());
    }

    @Override
    public boolean supports(Object handler) {
        return (handler instanceof HandlerMethod);
    }

    @Override
    public ModelAndView handleRequest(Object handler, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HandlerMethod hm = (HandlerMethod) handler;
        Object controller = hm.getHandler();
        Method method = hm.getMethod();
        // 请求参数：http://localhost:8080/user/query?id=10&name=lisi
        Object[] args = handleParameters(req, controller, method);

        try {
            Object returnValue = method.invoke(controller, args);
            handleReturnValue(returnValue,method,resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return null;
    }

    private void handleReturnValue(Object returnValue, Method method, HttpServletResponse resp) throws Exception{
        if (method.isAnnotationPresent(ResponseBody.class)) {
            Class<?> returnType = method.getReturnType();
            if (returnType == String.class) {
                resp.setContentType("text/plain;charset=utf-8");
                resp.getWriter().println(returnValue.toString());
            } else {
                resp.setContentType("application/json;charset=utf-8");
                // 将对象编程JSON字符串
                resp.getWriter().println(JsonUtils.object2Json(returnValue));
            }
        } else {
            // 视图处理
        }
    }

    private Object[] handleParameters(HttpServletRequest req, Object controller, Method method) {
        List<Object> paramList = new ArrayList<>();
        Map<String, String[]> parameterMap = req.getParameterMap();
        Parameter[] parameters = method.getParameters();

        Object value = null;

        for (Parameter parameter : parameters) {
            String name = parameter.getName();
            Class<?> parameterType = parameter.getType();
            String[] strings = parameterMap.get(name);
            value = resolveValue(parameterType, strings);

            paramList.add(value);
        }



        return paramList.toArray();
    }
    private Object resolveValue (Class<?> parameterType, String[] strings) {
        for (TypeHandler typeHandler : typeHandlers) {
            if (typeHandler.supports(parameterType)) {
                return typeHandler.resolve(strings);
            }
        }
        return null;
    }
}
