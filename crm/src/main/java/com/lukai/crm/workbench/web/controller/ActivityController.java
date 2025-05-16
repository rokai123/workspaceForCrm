package com.lukai.crm.workbench.web.controller;

import com.lukai.crm.settings.domain.User;
import com.lukai.crm.settings.service.UserService;
import com.lukai.crm.settings.service.impl.UserServiceImpl;
import com.lukai.crm.utils.MD5Util;
import com.lukai.crm.utils.PrintJson;
import com.lukai.crm.utils.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/workbench/activity/getUserList.do")
public class ActivityController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ActivityControllerクラスに入る");

        String Path = request.getServletPath();

        if ("/workbench/activity/getUserList.do".equals(Path)) {
            getUserList(request,response);

        }else if ("/workbench/activity/xxx.do".equals(Path)) {

        }
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("获取用户信息列表");
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();

        PrintJson.printJsonObj(response,uList);
        System.out.println("执行完毕，返回数据");
    }



}
