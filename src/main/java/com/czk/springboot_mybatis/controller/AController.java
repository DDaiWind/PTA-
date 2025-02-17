package com.czk.springboot_mybatis.controller;


import com.czk.springboot_mybatis.pojo.AEntity;
import com.czk.springboot_mybatis.service.AService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/a-entities")
public class AController {
    private final AService aService;

    public AController(AService aService) {
        this.aService = aService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AEntity entity) {
        return ResponseEntity.ok(aService.create(entity));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody AEntity entity) {
        return ResponseEntity.ok(aService.update(entity));
    }

    @DeleteMapping("/{a}")
    public ResponseEntity<?> delete(@PathVariable String a) {
        return ResponseEntity.ok(aService.delete(a));
    }

    @GetMapping("/search")
    public ResponseEntity<List<AEntity>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(aService.search(keyword));
    }

    @PostMapping("/import")
    public ResponseEntity<?> importExcel(@RequestParam("file") MultipartFile file) {
        try {
            validateFile(file);
            aService.importFromExcel(file);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "imported", "数据已成功入库"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("上传文件不能为空");
        }
        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.toLowerCase().endsWith(".xlsx")) {
            throw new RuntimeException("仅支持.xlsx格式文件");
        }
    }

    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) {
        aService.exportToExcel(response);
    }
}
