package com.czk.springboot_mybatis.service.impl;

import com.czk.springboot_mybatis.common.BusinessException;
import com.czk.springboot_mybatis.mapper.BMapper;
import com.czk.springboot_mybatis.pojo.BEntity;
import com.czk.springboot_mybatis.service.BService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Service
@Transactional
public class BServiceImpl implements BService {
    private final BMapper bMapper;

    @Autowired
    public BServiceImpl(BMapper bMapper) {
        this.bMapper = bMapper;
    }

    @Override
    public void create(BEntity entity) throws BusinessException {
        bMapper.insert(entity);
    }

    @Override
    public void update(BEntity entity) throws BusinessException {
        int affected = bMapper.update(entity);
        if (affected == 0) {
            throw new BusinessException("更新失败，记录不存在");
        }
    }

    @Override
    public void deleteByCompositeKey(String a, String c) throws BusinessException {
        int affected = bMapper.deleteByCompositeKey(a, c);
        if (affected == 0) {
            throw new BusinessException("删除失败，记录不存在");
        }
    }

    @Override
    public int deleteByC(String c) {
        return bMapper.deleteByC(c);
    }

    @Override
    public List<BEntity> findAll() {
        return bMapper.selectAll();
    }

    @Override
    public void importFromExcel(MultipartFile file) throws IOException {
        List<BEntity> entities = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                BEntity entity = parseRow(row);
                validateEntity(row, entity);
                entities.add(entity);
            }
        }

        int batchSize = 1000;
        for (int i = 0; i < entities.size(); i += batchSize) {
            List<BEntity> subList = entities.subList(i, Math.min(i + batchSize, entities.size()));
            bMapper.batchInsert(subList);
        }
    }

    private BEntity parseRow(Row row) {
        BEntity entity = new BEntity();
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
        entity.setCorrectAa(getCellBoolean(row, 10));
        entity.setCorrectBb(getCellBoolean(row, 11));
        entity.setCorrectCc(getCellBoolean(row, 12));
        entity.setCorrectDd(getCellBoolean(row, 13));
        entity.setCorrectEe(getCellBoolean(row, 14));
        return entity;
    }

    private void validateEntity(Row row, BEntity entity) {
        if (StringUtils.isBlank(entity.getA())) {
            throw new RuntimeException("第 " + (row.getRowNum()+1) + " 行主键a不能为空");
        }
        if (StringUtils.isBlank(entity.getC())) {
            throw new RuntimeException("第 " + (row.getRowNum()+1) + " 行主键c不能为空");
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
            List<BEntity> dataList = bMapper.selectAll();

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            String fileName = URLEncoder.encode("B表数据导出.xlsx", StandardCharsets.UTF_8);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("B表数据");

                String[] headers = {"主键a", "字段b", "字段c", "字段d", "字段e",
                        "aa", "bb", "cc", "dd", "ee",
                        "correct_aa", "correct_bb", "correct_cc","correct_dd", "correct_ee"};
                createHeaderRow(sheet, headers);

                int rowNum = 1;
                for (BEntity entity : dataList) {
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

    private void populateRow(Row row, BEntity entity) {
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
        row.createCell(colNum++).setCellValue(entity.getEe());
        row.createCell(colNum++).setCellValue(entity.getCorrectAa());
        row.createCell(colNum++).setCellValue(entity.getCorrectBb());
        row.createCell(colNum++).setCellValue(entity.getCorrectCc());
        row.createCell(colNum++).setCellValue(entity.getCorrectDd());
        row.createCell(colNum).setCellValue(entity.getCorrectEe());
    }

    public int calculateAaSum() {
        return bMapper.sumAa() != null ? bMapper.sumAa() : 0;
    }
    public int calculateBbSum() {
        return bMapper.sumBb() != null ? bMapper.sumBb() : 0;
    }
    public int calculateCcSum() {
        return bMapper.sumCc() != null ? bMapper.sumCc() : 0;
    }
    public int calculateDdSum() {
        return bMapper.sumDd() != null ? bMapper.sumDd() : 0;
    }
    public int calculateEeSum() {
        return bMapper.sumEe() != null ? bMapper.sumEe() : 0;
    }

    public int calculateCAaSum() {
        return bMapper.sumCAa() != null ? bMapper.sumCAa() : 0;
    }
    public int calculateCBbSum() {
        return bMapper.sumCBb() != null ? bMapper.sumCBb() : 0;
    }
    public int calculateCCcSum() {
        return bMapper.sumCCc() != null ? bMapper.sumCCc() : 0;
    }
    public int calculateCDdSum() {
        return bMapper.sumCDd() != null ? bMapper.sumCDd() : 0;
    }
    public int calculateCEeSum() {
        return bMapper.sumCEe() != null ? bMapper.sumCEe() : 0;
    }
}

