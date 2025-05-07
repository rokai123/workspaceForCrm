package com.lukai.crm.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

@WebFilter(
        filterName = "EncodingFilter",
        urlPatterns = "*.do"
)
public class EncodingFilter implements Filter {


    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("文字エンコーディングフィルターに入る");
        //过滤post请求日文参数乱码
        //POSTリクエストの日本語パラメータ文字化けをフィルタリングする
        req.setCharacterEncoding("utf-8");
        //レスポンスの日本語文字化けをフィルタリングする
        resp.setContentType("text/html;charset=utf-8");
        //放行
        //リクエストを通過させる
        filterChain.doFilter(req, resp);
    }

}
