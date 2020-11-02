package com.mthree.orderbook.interceptor;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
@ToString
public class RequestHeaderInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(request.getRequestURL().toString());
        log.info("[Request][" + request.getMethod() + " " + request.getRequestURL().toString() + " Params[" + request.getQueryString() + "]" + "]");
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        if (ex != null) {
            ex.printStackTrace();
        }
        log.info("[Response][" + response.getStatus() + "][exception: " + ex + "]");
    }

}
