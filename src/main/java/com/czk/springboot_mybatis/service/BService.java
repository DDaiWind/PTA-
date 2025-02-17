package com.czk.springboot_mybatis.service;

import com.czk.springboot_mybatis.common.BusinessException;
import com.czk.springboot_mybatis.pojo.BEntity;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface BService {
    // 新增（带联合唯一校验）
    void create(BEntity entity) throws BusinessException;

    // 更新
    void update(BEntity entity) throws BusinessException;

    // 删除
    void deleteByCompositeKey(String a, String c) throws BusinessException;
    int deleteByC(String c);

    // 导入导出
    void importFromExcel(MultipartFile file) throws IOException;
    public void exportToExcel(HttpServletResponse response);

    // 查询
    List<BEntity> findAll();

    public int calculateAaSum();
    public int calculateBbSum();
    public int calculateCcSum();
    public int calculateDdSum();
    public int calculateEeSum();

    public int calculateCAaSum();
    public int calculateCBbSum();
    public int calculateCCcSum();
    public int calculateCDdSum();
    public int calculateCEeSum();
}
