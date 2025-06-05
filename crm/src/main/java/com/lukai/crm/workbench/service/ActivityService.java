package com.lukai.crm.workbench.service;

import com.lukai.crm.vo.PaginationVO;
import com.lukai.crm.workbench.domain.Activity;

import java.util.Map;

public interface ActivityService {
    boolean save(Activity activity);
    PaginationVO<Activity> pageList(Map<String,Object> map);

    boolean delete(String[] ids);
}
