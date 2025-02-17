package com.czk.springboot_mybatis.pojo;

import lombok.Data;

@Data
public class BEntity {
    private String a; // 外键关联A表
    private String b;
    private String c; // 和a是联合唯一字段
    private String d;
    private String e;
    private Boolean aa;
    private Boolean bb;
    private Boolean cc;
    private Boolean dd;
    private Boolean ee;
    private Boolean correctAa;
    private Boolean correctBb;
    private Boolean correctCc;
    private Boolean correctDd;
    private Boolean correctEe;
}
