package com.ocbc.spring.mvc.maping;

import com.ocbc.spring.mvc.annotation.RayController;
import com.ocbc.spring.mvc.annotation.RequestMapping;
import com.ocbc.spring.mvc.handler.HandlerMethod;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RequestMappingHandlerMapping implements HandlerMapping, InitializingBean, BeanFactoryAware {
    // key就是URL，value就是HandlerMethod对象
    private Map<String, HandlerMethod> urlHandlerMethods = new HashMap<>();
    private DefaultListableBeanFactory beanFactory;

    @Override
    public Object getHandler(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (uri == null || "".equals(uri)){
            return null;
        }
        return this.urlHandlerMethods.get(uri);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            Class<?> clazzType = beanDefinition.getClass();
            if (isHandler(clazzType)) {
                RequestMapping requestMapping = clazzType.getAnnotation(RequestMapping.class);
                String classUrl = requestMapping.value();
                Method [] methods = clazzType.getDeclaredMethods();

                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
                        String methodUrl = methodRequestMapping.value();
                        String url = combine(classUrl, methodUrl);
                        HandlerMethod hm = new HandlerMethod(beanFactory.getBean(beanDefinitionName), method);
                        this.urlHandlerMethods.put(url, hm);
                    }
                }

            }
        }

    }

    private String combine(String clazzUrl, String methodUrl) {
        if (clazzUrl != null && !"".equals(clazzUrl) && !clazzUrl.startsWith("/")) {
            clazzUrl = "/" + clazzUrl;
        }
        if (!methodUrl.startsWith("/")){
            methodUrl = "/"+methodUrl;
        }
        StringBuffer sb = new StringBuffer();
        return sb.append(clazzUrl).append(methodUrl).toString();

    }
    private boolean isHandler(Class<?> clazzType) {
        return clazzType.isAnnotationPresent(RayController.class) || clazzType.isAnnotationPresent(RequestMapping.class);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }
}
