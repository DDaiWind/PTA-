package com.czk.springboot_mybatis.common;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}