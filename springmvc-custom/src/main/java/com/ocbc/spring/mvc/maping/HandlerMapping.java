package com.ocbc.spring.mvc.maping;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    Object getHandler(HttpServletRequest request);
}
