package com.lukai.crm.web.filter;

import com.lukai.crm.settings.domain.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LoginFilter implements Filter {


    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println("ログイン状態を検証するフィルター");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        //如果user不为空说明登录过，放行
        //userがnullでない場合、ログイン済みと判断し通過を許可する
        if (user == null) {

        }
    }

}
