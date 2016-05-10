package com.fjnu.bookstore.user.web.servlet;

import com.fjnu.bookstore.user.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by lenovo on 2016/3/22.
 */
@WebFilter(filterName = "LoginFilter",urlPatterns = {"/jsps/order/*","/jsps/cart/*"},servletNames = {"OrderServlet","CartServlet"})
public class LoginFilter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        /*
		 * 1. 从session中获取用户信息
		 * 2. 判断如题session中存在用户信息，放行！
		 * 3. 否则，保存错误信息，转发到login.jsp显示
		 */
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        User user = (User) httpRequest.getSession().getAttribute("session_user");
        if (user != null) {
            chain.doFilter(request, response);
        } else {
            httpRequest.setAttribute("msg", "您还没有登录！");
            httpRequest.getRequestDispatcher("/jsps/user/login.jsp")
                    .forward(httpRequest, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
