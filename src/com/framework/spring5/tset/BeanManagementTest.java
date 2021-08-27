package com.framework.spring5.tset;

import com.framework.spring5.BeanManagement;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

public class BeanManagementTest {
    @Test
    public void BeanManagementTest(){
        ApplicationContext context=
                new ClassPathXmlApplicationContext("namespacetry.xml");
        BeanManagement lbw=context.getBean("manager",BeanManagement.class);
        System.out.println(lbw);
        lbw.showBeanInfo();
    }

}