package com.framework.spring5.Dao;


import org.springframework.stereotype.Repository;

@Repository
public class oneDaoImpl implements oneDao {
    @Override
    public void add() {
        System.out.println("add .......");
    }
}
