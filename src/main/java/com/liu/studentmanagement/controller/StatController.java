package com.liu.studentmanagement.controller;

import com.liu.studentmanagement.common.Result;
import com.liu.studentmanagement.entity.vo.DashboardVO;
import com.liu.studentmanagement.service.studentService.IStudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stat")
@Tag(name = "统计分析")
@CrossOrigin
public class StatController {

    @Autowired
    private IStudentService studentService;

    @GetMapping("/dashboard")
    @Operation(summary = "获取首页概览数据")
    public Result<DashboardVO> getDashboard() {
        return Result.success(studentService.getDashboardStats());
    }
}