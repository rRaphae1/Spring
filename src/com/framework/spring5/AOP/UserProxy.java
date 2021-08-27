package com.framework.spring5.AOP;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UserProxy {
    @Before(value="execution(* com.framework.spring5.AOP.NewUser.add(..))")
    public void before(){
        System.out.println("Before");
    }

    @Before(value="execution(* com.framework.spring5.Dao.oneDao.add(..))")
    public void woc(){
        System.out.println("woc");
    }

    @Around(value="execution(* com.framework.spring5.Dao.oneDao.add(..))")//环绕通知
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        //环绕之前
        System.out.println("环绕前");
        //环绕通知的增强逻辑
        proceedingJoinPoint.proceed();
        //环绕之后
        System.out.println("环绕后");
    }
}
