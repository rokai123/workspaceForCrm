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
import java.util.Map;

@WebServlet(urlPatterns = "/workbench/activity/getUserList.do")
public class ActivityController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ActivityControllerクラスに入る");

        String Path = request.getServletPath();

        if ("/workbench/activity/getUserList.do".equals(Path)) {


        }else if ("/workbench/activity/xxx.do".equals(Path)) {

        }
    }


}
