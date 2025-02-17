package com.czk.springboot_mybatis.controller;

import com.czk.springboot_mybatis.common.BusinessException;
import com.czk.springboot_mybatis.common.Result;
import com.czk.springboot_mybatis.pojo.BEntity;
import com.czk.springboot_mybatis.service.BService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/b")
public class BController {
    private final BService bService;

    @Autowired
    public BController(BService bService) {
        this.bService = bService;
    }

    @PostMapping
    public Result<Void> create(@RequestBody BEntity entity) {
        bService.create(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody BEntity entity) {
        bService.update(entity);
        return Result.success();
    }

    @DeleteMapping("/{a}/{c}")
    public Result<Void> deleteByCompositeKey(@PathVariable String a,
                                             @PathVariable String c) {
        bService.deleteByCompositeKey(a, c);
        return Result.success();
    }

    @DeleteMapping("/c/{c}")
    public Result<Integer> deleteByC(@PathVariable String c) {
        int count = bService.deleteByC(c);
        return Result.success(count);
    }

    @PostMapping("/import")
    public Result<Void> importExcel(@RequestParam("file") MultipartFile file) {
        try {
            bService.importFromExcel(file);
            return Result.success();
        } catch (IOException e) {
            throw new BusinessException("文件导入失败: " + e.getMessage());
        }
    }

    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) {
        bService.exportToExcel(response);
    }

    @GetMapping
    public Result<List<BEntity>> findAll() {
        List<BEntity> data = bService.findAll();
        return Result.success(data);
    }
}
