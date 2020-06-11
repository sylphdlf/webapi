package com.dlf.web.filter;

import com.dlf.web.utils.WebUtils;
import com.google.common.collect.ImmutableList;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UrlRedirectFilter implements Filter{

    private static final ImmutableList<String> ignoreUri = ImmutableList.of("/login", "/register", "/logout", "/unAuth", "/file/upload", "/file/download");

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
