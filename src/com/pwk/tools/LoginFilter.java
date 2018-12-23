package com.pwk.tools;

import com.pwk.entity.Admin;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-25
 * Time: 下午11:03
 * To change this template use File | Settings | File Templates.
 */
public class LoginFilter implements Filter {
    @SuppressWarnings("unused")
    private FilterConfig filterConfig;
    private FilterChain chain;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        this.chain = filterChain;
        this.request = (HttpServletRequest) servletRequest;
        this.response = ((HttpServletResponse) servletResponse);
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        if (admin == null) {
            response.sendRedirect(request.getContextPath()+"/login.jsp");
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}
