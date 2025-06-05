package com.lukai.crm.workbench.web.controller;

import com.lukai.crm.settings.domain.User;
import com.lukai.crm.settings.service.UserService;
import com.lukai.crm.settings.service.impl.UserServiceImpl;
import com.lukai.crm.utils.*;
import com.lukai.crm.vo.PaginationVO;
import com.lukai.crm.workbench.domain.Activity;
import com.lukai.crm.workbench.service.ActivityService;
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

@WebServlet(urlPatterns = {"/workbench/activity/getUserList.do","/workbench/activity/save.do",
        "/workbench/activity/pageList.do","/workbench/activity/delete.do"})
public class ActivityController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ActivityControllerクラスに入る");

        String Path = request.getServletPath();

        if ("/workbench/activity/getUserList.do".equals(Path)) {
            getUserList(request,response);

        }else if ("/workbench/activity/save.do".equals(Path)) {
            save(request,response);
        }else if ("/workbench/activity/pageList.do".equals(Path)) {
            pageList(request,response);
            
        } else if ("/workbench/activity/delete.do".equals(Path)) {
            delete(request,response);

        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动的删除操作");
        String[] ids = request.getParameterValues("id");
        ActivityService activityService = (ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = activityService.delete(ids);

        PrintJson.printJsonFlag(response, flag);

    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到查询市场活动信息列表的操作（结合条件查询+分页查询）");

        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String pageNoStr = request.getParameter("pageNo");
        //将字符串转换为int类型
        int pageNo = Integer.parseInt(pageNoStr);
        //每页展现的记录数
        String pageSizeStr = request.getParameter("pageSize");
        int pageSize = Integer.parseInt(pageSizeStr);
        //计算出略过的记录数（总结出的算式）
        int skipCount = (pageNo-1)*pageSize;

        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("pageSize",pageSize);
        map.put("skipCount",skipCount);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        PaginationVO<Activity> vo = as.pageList(map);
        PrintJson.printJsonObj(response, vo);


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
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.save(activity);
        PrintJson.printJsonFlag(response, flag);

    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("获取用户信息列表");
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();

        PrintJson.printJsonObj(response,uList);
        System.out.println("执行完毕，返回数据");
    }



}
