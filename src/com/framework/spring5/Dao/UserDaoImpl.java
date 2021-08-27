package com.framework.spring5.Dao;


import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl implements UserDao {
    @Override
    public void call() {
        System.out.println("call!");
    }

    @Override
    public int add(int a, int b) {
        System.out.println("Add");
        return a+b;
    }
}
