package com.dlf.web.filter;

import com.google.common.collect.ImmutableList;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;

import javax.servlet.*;
import java.io.IOException;

public class UrlRedirectFilter implements Filter{

    private static final ImmutableList<String> ignoreUri = ImmutableList.of("/login", "/register", "/logout", "/unAuth");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String servletPath = ((ShiroHttpServletRequest) servletRequest).getServletPath();
        if(ignoreUri.contains(servletPath)){
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            servletRequest.setAttribute("url", servletPath);
            servletRequest.getRequestDispatcher("/comm").forward(servletRequest, servletResponse);
        }
    }
}
