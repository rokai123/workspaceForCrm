package com.lukai.crm.settings.service;

import com.lukai.crm.exception.LoginException;
import com.lukai.crm.settings.domain.User;

import java.util.List;

public interface UserService {

    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserList();
}
