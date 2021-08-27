package com.framework.spring5.tset;


import com.framework.spring5.AOP.NewUser;
import com.framework.spring5.Config;
import com.framework.spring5.FirstClass;
import com.framework.spring5.Service.ManagerService;
import com.framework.spring5.Service.UserService;
import com.framework.spring5.Service.oneService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Spring5Test {

    @Test
    public void FirstClassTest(){
        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring-config.xml");
        FirstClass lbw=context.getBean("owner", FirstClass.class);
        System.out.println(lbw);
        lbw.showinfo();

    }


    @Test
    public void UserTest()
    {
        ApplicationContext contex= new ClassPathXmlApplicationContext("spring-config.xml");
        UserService user=contex.getBean("user",UserService.class);
        user.call();
    }

    @Test
    public void ManagerTest()
    {
        ApplicationContext context=new ClassPathXmlApplicationContext("spring-config.xml");
        ManagerService managerService = context.getBean("managerService", ManagerService.class);
        System.out.println(managerService);
        managerService.call();
    }

    @Test
    public void oneTest(){
        ApplicationContext context=new ClassPathXmlApplicationContext("spring-config.xml");
        oneService OneService =context.getBean("OneService",oneService.class);
        OneService.add();
    }

    @Test
    public void oneNextTest(){
        ApplicationContext context=new AnnotationConfigApplicationContext(Config.class);
        oneService OneService=context.getBean("OneService",oneService.class);
        OneService.add();
    }

    @Test
    public void AopTest(){
        ApplicationContext context=new ClassPathXmlApplicationContext("spring-config.xml");
        NewUser newUser = context.getBean("newUser", NewUser.class);
        newUser.add();
    }

}
