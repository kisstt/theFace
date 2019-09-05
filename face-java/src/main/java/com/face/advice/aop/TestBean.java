package com.face.advice.aop;

import org.springframework.stereotype.Component;

@Component
public class TestBean {
    private String stu = "TestStu";

    public String getStu() {
        System.out.println("getStu():" + stu);
        return stu;
    }

    public void setStu(String str) {
        this.stu = str;
    }
}
