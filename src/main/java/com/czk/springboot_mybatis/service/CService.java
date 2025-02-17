package com.czk.springboot_mybatis.service;

import com.czk.springboot_mybatis.pojo.CEntity;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;


public interface CService {

    void calculateStats();

    List<CEntity> searchByB(String keyword);

    List<CEntity> selectAll();

    public void exportToExcel(HttpServletResponse response);
}
