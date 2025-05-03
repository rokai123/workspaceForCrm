package com.lukai.crm.settings.web.controller;

import com.lukai.crm.settings.domain.User;
import com.lukai.crm.settings.service.UserService;
import com.lukai.crm.settings.service.impl.UserServiceImpl;
import com.lukai.crm.utils.MD5Util;
import com.lukai.crm.utils.PrintJson;
import com.lukai.crm.utils.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/settings/user/login.do")
public class UserController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("UserControllerクラスに入る");

        String Path = request.getServletPath();

        if ("/settings/user/login.do".equals(Path)) {
            login(request,response);


        }else if ("/settings/user/xxx.do".equals(Path)) {

        }
    }

    private void login(HttpServletRequest request,HttpServletResponse response){

        //接收参数
        //リクエストボディからパラメーターを取得する
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        //将明文密码转换为MD5的密文形式
        //平文パスワードをMD5ハッシュに変換
        loginPwd = MD5Util.getMD5(loginPwd);
        //接收IP地址
        //IPアドレスを受信する
        String ip = request.getRemoteAddr();
        //业务层的开发，统一使用代理类形态的接口对象
        //ビジネスロジック層では、プロキシクラス形式のインターフェースオブジェクトを統一採用
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        try {
            User user = us.login(loginAct,loginPwd);
            request.getSession().setAttribute("user", user);
            //程序执行到此处，说明业务层没有为controller抛出任何异常，表示登录成功。
            //プログラムがこの位置まで実行された場合、ビジネスロジック層がControllerに例外を送出していないため、ログインが成功したことを示します。
            PrintJson.printJsonFlag(response,true);
        }catch (Exception e) {
            e.printStackTrace();
            //程序一旦执行了catch语句块的信息，说明业务层验证了登录失败，并且为controller层抛出了异常。(カスタム例外)
            //プログラムがcatchブロックを実行した場合、ビジネスロジック層でログイン検証が失敗し、Controller層に例外が送出されたことを示します
            //{"success":false,"msg":?}
            String msg = e.getMessage();
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("success",false);
            map.put("msg", msg);
            PrintJson.printJsonObj(response,map);


        }



    }
}
