package com.lukai.crm.workbench.web.controller;

import com.lukai.crm.settings.domain.User;
import com.lukai.crm.settings.service.UserService;
import com.lukai.crm.settings.service.impl.UserServiceImpl;
import com.lukai.crm.utils.DateTimeUtil;
import com.lukai.crm.utils.MD5Util;
import com.lukai.crm.utils.PrintJson;
import com.lukai.crm.utils.ServiceFactory;
import com.lukai.crm.workbench.domain.Activity;
import com.lukai.crm.workbench.service.impl.ActivityServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet(urlPatterns = {"/workbench/activity/getUserList.do","/workbench/activity/save.do"})
public class ActivityController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ActivityControllerクラスに入る");

        String Path = request.getServletPath();

        if ("/workbench/activity/getUserList.do".equals(Path)) {
            getUserList(request,response);

        }else if ("/workbench/activity/save.do".equals(Path)) {
            save(request,response);
        }
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入添加市场活动控制器");
        //从前端拿到用户提交的数据
        String id = UUID.randomUUID().toString();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");

        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        //将数据封装到市场活动对象中
        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);

        ActivityServiceImpl as = (ActivityServiceImpl) ServiceFactory.getService(new ActivityServiceImpl());
        

    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("获取用户信息列表");
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();

        PrintJson.printJsonObj(response,uList);
        System.out.println("执行完毕，返回数据");
    }



}
