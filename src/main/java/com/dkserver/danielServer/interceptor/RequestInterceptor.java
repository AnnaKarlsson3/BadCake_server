package com.dkserver.danielServer.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import static com.dkserver.danielServer.utils.Constants.*;

@Component
//get the tenantID from RequestHeader
public class RequestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object object) throws Exception {

        String path = request.getRequestURI();

        // Skip filter for register and login endpoints
        if (path.startsWith(REST_AUTH)){
            return true;
        }

        String requestURI = request.getRequestURI();
        String tenantID = request.getHeader(HEADER_TENANT);

        if (tenantID == null) {
            response.getWriter().write(NO_HEADER_TENANT);
            response.setStatus(400);
            return false;
        }
        TenantContext.setCurrentTenant(tenantID);
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        TenantContext.clear();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TenantContext.clear();
    }
}