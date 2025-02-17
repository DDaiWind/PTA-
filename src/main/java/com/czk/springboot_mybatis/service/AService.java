package com.czk.springboot_mybatis.service;

import com.czk.springboot_mybatis.pojo.AEntity;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface AService {
    int create(AEntity entity);
    int update(AEntity entity);
    int delete(String a);
    List<AEntity> search(String keyword);
    void importFromExcel(MultipartFile file) throws IOException;
    public void exportToExcel(HttpServletResponse response);

    public int calculateAaSum();
    public int calculateBbSum();
    public int calculateCcSum();
    public int calculateDdSum();
    public int calculateEeSum();
}
