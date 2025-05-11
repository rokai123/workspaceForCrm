package com.lukai.crm.workbench.service.impl;

import com.lukai.crm.utils.SqlSessionUtil;
import com.lukai.crm.workbench.dao.ActivityDao;
import com.lukai.crm.workbench.service.ActivityService;

public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    
}
