package com.framework.spring5.Proxy;

import com.framework.spring5.Dao.UserDao;
import com.framework.spring5.Dao.UserDaoImpl;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class JDKProxy {
    public static void main(String[] args) {
        //创建接口实现类的代理对象
        Class[] interfaces={UserDao.class};
//        Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                return null;
//            }
//        })
        UserDaoImpl userDao=new UserDaoImpl();
        UserDao dao = (UserDao)Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new UserDaoProxy(userDao));
        System.out.println(dao.add(1,2));
    }
}

class UserDaoProxy implements InvocationHandler{
    //1.把代理对象所代理的类传递过来
    //通过有参构造传递,为了能够更通用，使用了Object
    private Object obj;
    public UserDaoProxy(Object obj){
        this.obj=obj;
    }

    @Override
    //对象创建就会调用该方法，在这里面写增强逻辑
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //方法之前执行
        System.out.println("previous!!"+method.getName()+Arrays.toString(args));
        //被增强的方法执行
        Object res=method.invoke(obj,args);
        //方法之后执行
        System.out.println("after!!"+obj);
        return res;
    }
}