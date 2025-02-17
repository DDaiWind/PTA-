package com.czk.springboot_mybatis.service.impl;

import com.czk.springboot_mybatis.mapper.CMapper;
import com.czk.springboot_mybatis.pojo.AEntity;
import com.czk.springboot_mybatis.pojo.CEntity;
import com.czk.springboot_mybatis.service.CService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class CServiceImpl implements CService {
    @Autowired
    private CMapper cMapper;

    @Override
    public void calculateStats() {
        cMapper.calculateStats();
    }

    @Override
    public List<CEntity> searchByB(String keyword) {
        return cMapper.searchByB(keyword);
    }

    @Override
    public List<CEntity> selectAll() {
        return cMapper.selectAll();
    }

    public void exportToExcel(HttpServletResponse response) {
        try {
            List<CEntity> dataList = cMapper.selectAll();

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            String fileName = URLEncoder.encode("C表数据导出.xlsx", StandardCharsets.UTF_8);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("C表数据");

                String[] headers = {"序号a", "主键b", "aaS", "bbS", "ccS",
                        "ddS", "aaA", "bbA", "ccA", "aaSS", "aaC"};
                createHeaderRow(sheet, headers);

                int rowNum = 1;
                for (CEntity entity : dataList) {
                    Row row = sheet.createRow(rowNum++);
                    populateRow(row, entity);
                }

                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                workbook.write(response.getOutputStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("导出失败: " + e.getMessage());
        }
    }

    private void createHeaderRow(Sheet sheet, String[] headers) {
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = createHeaderStyle(sheet.getWorkbook());

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private void populateRow(Row row, CEntity entity) {
        int colNum = 0;
        row.createCell(colNum++).setCellValue(entity.getA());
        row.createCell(colNum++).setCellValue(entity.getB());
        row.createCell(colNum++).setCellValue(entity.getAaS());
        row.createCell(colNum++).setCellValue(entity.getBbS());
        row.createCell(colNum++).setCellValue(entity.getCcS());
        row.createCell(colNum++).setCellValue(entity.getDdS());
        row.createCell(colNum++).setCellValue(entity.getAaA());
        row.createCell(colNum++).setCellValue(entity.getBbA());
        row.createCell(colNum++).setCellValue(entity.getCcA());
        row.createCell(colNum++).setCellValue(entity.getAaSS());
        row.createCell(colNum).setCellValue(entity.getAaC());
    }
}
