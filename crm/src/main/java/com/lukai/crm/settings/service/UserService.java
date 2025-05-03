package com.lukai.crm.settings.service;

import com.lukai.crm.settings.domain.User;

public interface UserService {

    User login(String loginAct, String loginPwd);
}
