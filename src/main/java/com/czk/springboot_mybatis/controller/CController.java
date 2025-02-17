package com.czk.springboot_mybatis.controller;

import com.czk.springboot_mybatis.pojo.AEntity;
import com.czk.springboot_mybatis.pojo.CEntity;
import com.czk.springboot_mybatis.service.AService;
import com.czk.springboot_mybatis.service.CService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/c-entities")
public class CController {

    private final CService cService;

    public CController(CService cService) {
        this.cService = cService;
    }
    @PostMapping("/calculate")
    public String calculateStats() {
        cService.calculateStats();
        return "计算任务已触发";
    }
    @GetMapping("/search")
    public ResponseEntity<List<CEntity>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(cService.searchByB(keyword));
    }

    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) {
        cService.exportToExcel(response);
    }
}