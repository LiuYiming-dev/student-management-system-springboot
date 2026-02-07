package com.liu.studentmanagement.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.studentmanagement.service.clazzService.IClazzService;
import com.liu.studentmanagement.common.PageResult;
import com.liu.studentmanagement.common.Result;
import com.liu.studentmanagement.entity.Clazz;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clazz")
@CrossOrigin
@Tag(name = "班级管理模块", description = "负责班级的增删改查")
public class ClazzController {
    @Autowired
    private IClazzService clazzService;

    @PostMapping("/add")
    @Operation(summary = "新增班级") // 🌟 描述这个接口
    public Result<?> add(@RequestBody @Validated Clazz clazz) {
        clazzService.save(clazz);
        return Result.success(null);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}") // 稍微规范一点，用 @DeleteMapping
    @Operation(summary = "删除班级") // 🌟 描述这个接口
    public Result<?> delete(@PathVariable Integer id) {
        clazzService.deleteClazzStudents(id);
        return Result.success(null);
    }

    @PutMapping("/update")
    @Operation(summary = "更新班级信息") // 🌟 描述这个接口
    public Result<?> update(@RequestBody @Validated Clazz clazz) {
        clazzService.updateById(clazz);
        return Result.success(null);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询班级列表")
    public Result<Page<Clazz>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(clazzService.getClazzPage(pageNum, pageSize));

    }

    @GetMapping("/all")
    @Operation(summary = "查询所有班级(用于下拉框)")
    public Result<List<Clazz>> all() {
        return Result.success(clazzService.listAll());
    }
}
