package com.framework.spring5.AOP;


import org.springframework.stereotype.Component;

@Component
public class NewUser {
    public void add()
    {
        System.out.println("add now");
    }
}
