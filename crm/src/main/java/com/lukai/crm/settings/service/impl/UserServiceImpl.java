package com.lukai.crm.settings.service.impl;

import com.lukai.crm.exception.LoginException;
import com.lukai.crm.settings.dao.UserDao;
import com.lukai.crm.settings.domain.User;
import com.lukai.crm.settings.service.UserService;
import com.lukai.crm.utils.DateTimeUtil;
import com.lukai.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);

        User user = userDao.login(map);
        //NULLであれば、アカウントIDかパスワードが間違っている
        if (user == null) {
            throw new LoginException("アカウントIDまたはパスワードが正しくありません");
        }
        //如果程序能够成功的执行到该行，说明账号密码正确，需要继续向下验证其他3项信息
        //この行まで正常に処理が到達した場合、アカウントとパスワードの認証は成功しているため、残り3項目の検証を継続する必要がある

        //验证失效时间。（失効時間を検証する）
        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if (expireTime.compareTo(currentTime) < 0) {
            throw new LoginException("このアカウントは無効化されています");

        }

        //ロック状態を検証する
        String lockState = user.getLockState();
        if (lockState == null || lockState.equals("0")) {
            throw new LoginException("アカウントをロックされています");

        }

        //IPアドレスを検証する
        String allowIps = user.getAllowIps();
        if(!allowIps.contains(ip)) {
            throw new LoginException("許可されるIPアドレスではありません");
        }
        

        return user;
    }

    @Override
    public List<User> getUserList() {
        List<User> uList = userDao.getUserList();

        return uList;
    }
}
