package com.czk.springboot_mybatis.pojo;

import lombok.Data;

@Data
public class CEntity {
    private Integer a;// 自增
    private String b; // 主键
    private Double aaS; // 正确的aa总和/A表中aa总和
    private Double bbS; // 正确的bb总和/A表中bb总和
    private Double ccS; // 正确的cc总和/A表中cc总和
    private Double ddS; // 正确的dd总和/A表中dd总和
    private Double aaA; // 正确的aa总和/B表中aa总和
    private Double bbA; // 正确的aa总和/B表中aa总和
    private Double ccA; // 正确的aa总和/B表中aa总和
    private Double aaSS; // 正确的ee总和/A表中ee总和
    private Double aaC; // 正确的aa总数+正确的ee总数/A表中aa总数+A表中ee总数
}
