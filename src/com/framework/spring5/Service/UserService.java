package com.framework.spring5.Service;

import com.framework.spring5.Dao.UserDao;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void call()
    {
        userDao.call();
    }
}
