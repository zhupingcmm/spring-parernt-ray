package com.ocbc.spring.mvc.maping;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BeanNameUrlHandlerMapping implements HandlerMapping, BeanFactoryAware, InitializingBean {
    private final Map<String, Object> urlHandleMap = new HashMap<>();

    private DefaultListableBeanFactory beanFactory;
    @Override
    public Object getHandler(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (uri == null || uri.isEmpty()) return null;

        return urlHandleMap.get(uri);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            if (beanDefinitionName == null || beanDefinitionName.isEmpty()) {
                continue;
            }
            if (beanDefinitionName.startsWith("/")) {
                this.urlHandleMap.put(beanDefinitionName, beanFactory.getBean(beanDefinitionName));
            }
        }
    }
}
