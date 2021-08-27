package com.framework.spring5.Service;


import org.springframework.stereotype.Component;

@Component  //等价于<bean id="managerService" class="com.***">
public class ManagerService {
    public void call()
    {
        System.out.println("call!");
    }
}
