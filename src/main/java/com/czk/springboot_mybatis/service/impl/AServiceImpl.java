package com.czk.springboot_mybatis.service.impl;

import com.czk.springboot_mybatis.mapper.AMapper;
import com.czk.springboot_mybatis.pojo.AEntity;
import com.czk.springboot_mybatis.service.AService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class AServiceImpl implements AService {
    private final AMapper aMapper;

    public AServiceImpl(AMapper aMapper) {
        this.aMapper = aMapper;
    }

    @Override
    public int create(AEntity entity) {
        return aMapper.insert(entity);
    }

    @Override
    public int update(AEntity entity) {
        return aMapper.update(entity);
    }

    @Override
    public int delete(String a) {
        return aMapper.delete(a);
    }

    @Override
    public List<AEntity> search(String keyword) {
        return aMapper.searchByA(keyword);
    }

    @Override
    public void importFromExcel(MultipartFile file) throws IOException {
        List<AEntity> entities = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                AEntity entity = parseRow(row);
                validateEntity(row, entity);
                entities.add(entity);
            }
        }

        int batchSize = 1000;
        for (int i = 0; i < entities.size(); i += batchSize) {
            List<AEntity> subList = entities.subList(i, Math.min(i + batchSize, entities.size()));
            aMapper.batchInsert(subList);
        }
    }

    private AEntity parseRow(Row row) {
        AEntity entity = new AEntity();
        entity.setA(getCellString(row, 0));
        entity.setB(getCellString(row, 1));
        entity.setC(getCellString(row, 2));
        entity.setD(getCellString(row, 3));
        entity.setE(getCellString(row, 4));
        entity.setAa(getCellBoolean(row, 5));
        entity.setBb(getCellBoolean(row, 6));
        entity.setCc(getCellBoolean(row, 7));
        entity.setDd(getCellBoolean(row, 8));
        entity.setEe(getCellBoolean(row, 9));
        return entity;
    }

    private void validateEntity(Row row, AEntity entity) {
        if (StringUtils.isBlank(entity.getA())) {
            throw new RuntimeException("第 " + (row.getRowNum()+1) + " 行主键a不能为空");
        }
    }

    private String getCellString(Row row, int cellNum) {
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(row.getCell(cellNum)).trim();
    }

    private Boolean getCellBoolean(Row row, int cellNum) {
        Cell cell = row.getCell(cellNum);
        if (cell == null) return false;
        return cell.getBooleanCellValue();
    }
    public void exportToExcel(HttpServletResponse response) {
        try {
            List<AEntity> dataList = aMapper.selectAll();

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            String fileName = URLEncoder.encode("A表数据导出.xlsx", StandardCharsets.UTF_8);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("A表数据");

                String[] headers = {"主键a", "字段b", "字段c", "字段d", "字段e",
                        "aa", "bb", "cc", "dd", "ee"};
                createHeaderRow(sheet, headers);

                int rowNum = 1;
                for (AEntity entity : dataList) {
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

    private void populateRow(Row row, AEntity entity) {
        int colNum = 0;
        row.createCell(colNum++).setCellValue(entity.getA());
        row.createCell(colNum++).setCellValue(entity.getB());
        row.createCell(colNum++).setCellValue(entity.getC());
        row.createCell(colNum++).setCellValue(entity.getD());
        row.createCell(colNum++).setCellValue(entity.getE());
        row.createCell(colNum++).setCellValue(entity.getAa());
        row.createCell(colNum++).setCellValue(entity.getBb());
        row.createCell(colNum++).setCellValue(entity.getCc());
        row.createCell(colNum++).setCellValue(entity.getDd());
        row.createCell(colNum).setCellValue(entity.getEe());
    }

    public int calculateAaSum() {
        return aMapper.sumAa() != null ? aMapper.sumAa() : 0;
    }
    public int calculateBbSum() {
        return aMapper.sumBb() != null ? aMapper.sumBb() : 0;
    }
    public int calculateCcSum() {
        return aMapper.sumCc() != null ? aMapper.sumCc() : 0;
    }
    public int calculateDdSum() {
        return aMapper.sumDd() != null ? aMapper.sumDd() : 0;
    }
    public int calculateEeSum() {
        return aMapper.sumEe() != null ? aMapper.sumEe() : 0;
    }
}
