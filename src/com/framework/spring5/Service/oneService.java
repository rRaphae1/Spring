package com.framework.spring5.Service;

import com.framework.spring5.Dao.oneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service(value="OneService")
public class oneService {

    @Value(value="abc")
    private String name;
    @Autowired
    private oneDao OneDao;

    public void add(){
        System.out.println("begin add:"+name);
        OneDao.add();
    }
}
