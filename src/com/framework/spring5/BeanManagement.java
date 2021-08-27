package com.framework.spring5;

public class BeanManagement {
    private int ManagerAge;
    private String ManagerName;

    public void setManageAddress(String manageAddress) {
        ManageAddress = manageAddress;
    }

    private String ManageAddress;
    public void setManagerName(String managerName) {
        ManagerName = managerName;
    }

    public void setManagerAge(int managerAge) {
        ManagerAge = managerAge;
    }

    public void showBeanInfo()
    {
        System.out.println(ManagerAge+"岁的管理员 ::"+ManagerName+"家住 ::"+ManageAddress);
    }


}
