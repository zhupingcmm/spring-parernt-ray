package com.ocbc.spring.mvc.servlet;

import com.ocbc.spring.mvc.adapter.HandlerAdapter;
import com.ocbc.spring.mvc.maping.HandlerMapping;
import com.ocbc.spring.mvc.model.ModelAndView;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DispatchServlet extends AbstractServlet{
    private static final String contextConfigLocation = "contextConfigLocation";
    private List<HandlerMapping> handlerMappings = new ArrayList<>();
    private List<HandlerAdapter> handlerAdapters = new ArrayList<>();
    private DefaultListableBeanFactory beanFactory;


    @Override
    public void init(ServletConfig config) throws ServletException {
        String location = config.getInitParameter(contextConfigLocation);
        beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(location);

//        beanFactory.getBean(Object.class);

        // 初始化策略集合
        initStrategies();

    }

    private void initStrategies(){
        initHandlerMappings();
        initHandlerAdapters();
    }

    private void initHandlerMappings() {
        Map<String, HandlerMapping> beansOfType = beanFactory.getBeansOfType(HandlerMapping.class);
        handlerMappings = new ArrayList<>(beansOfType.values());
    }
    private void initHandlerAdapters() {
        Map<String, HandlerAdapter> beansOfType = beanFactory.getBeansOfType(HandlerAdapter.class);
        handlerAdapters = new ArrayList<>(beansOfType.values());
    }
    @Override
    protected void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object handler = getHandler(req);
        if (handler == null) return;

        HandlerAdapter handlerAdapter = getHandlerAdapter(handler);
        if (handlerAdapter == null) return;
        ModelAndView modelAndView = handlerAdapter.handleRequest(handler, req, resp);

    }


    private Object getHandler(HttpServletRequest req) {

        for (HandlerMapping handlerMapping : handlerMappings) {
            Object handler = handlerMapping.getHandler(req);
            if (handler != null) return handler;
        }
        return null;
    }

    private HandlerAdapter getHandlerAdapter(Object handler) {
        for (HandlerAdapter handlerAdapter : handlerAdapters) {
            if (handlerAdapter.supports(handler)) return handlerAdapter;
        }
        return null;
    }

}
