package com.lukai.crm.workbench.service.impl;

import com.lukai.crm.utils.SqlSessionUtil;
import com.lukai.crm.workbench.dao.ActivityDao;
import com.lukai.crm.workbench.domain.Activity;
import com.lukai.crm.workbench.service.ActivityService;

public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);

    @Override
    public boolean save(Activity activity) {
        boolean flag = true;

        int count = activityDao.save(activity);
        if (count!=1) {
            flag = false;
        }
       return flag;
    }
}
