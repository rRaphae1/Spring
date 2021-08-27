package com.framework.spring5;

public class FirstClass {

    private String ownername;
    private String ownerage;

    public FirstClass(String ownername, String ownerage) {
        this.ownername = ownername;
        this.ownerage=ownerage;
    }

    public void setOwnerage(String ownerage) {
        this.ownerage = ownerage;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public void startup(){
        System.out.println("this will be my first spring project!");
    }

    public void showinfo(){
        System.out.println(ownername+"::"+ownerage);
    }
}
