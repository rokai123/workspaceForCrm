package com.lukai.crm.workbench.service.impl;

import com.lukai.crm.utils.SqlSessionUtil;
import com.lukai.crm.vo.PaginationVO;
import com.lukai.crm.workbench.dao.ActivityDao;
import com.lukai.crm.workbench.dao.ActivityRemarkDao;
import com.lukai.crm.workbench.domain.Activity;
import com.lukai.crm.workbench.service.ActivityService;

import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);


    @Override
    public boolean save(Activity activity) {
        boolean flag = true;

        int count = activityDao.save(activity);
        if (count!=1) {
            flag = false;
        }
       return flag;
    }

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {
        //取得total
        int total = activityDao.getTotalByCondition(map);
        //取得dataList
        List<Activity> dataList = activityDao.getActivityListByCondition(map);
        //将total和dataList封装到vo中
        PaginationVO<Activity> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    @Override
    public boolean delete(String[] ids) {
        boolean flag = true;
        //查询出需要删除的备注的数量
        int remarkCount = activityRemarkDao.getCountByAids(ids);
        //删除备注，返回受到影响的条数（实际删除的数量）
        int affectedRows = activityRemarkDao.deleteByAids(ids);
        if (remarkCount!=affectedRows) {
            flag = false;
        }

        //删除市场活动
        int deletedActivityCount  = activityDao.delete(ids);
        if (deletedActivityCount!=ids.length) {
            flag = false;
        }
        return flag;
    }
}
