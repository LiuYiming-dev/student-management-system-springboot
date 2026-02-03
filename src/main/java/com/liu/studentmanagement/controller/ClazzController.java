package com.liu.studentmanagement.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.studentmanagement.service.IClazzService;
import com.liu.studentmanagement.common.PageResult;
import com.liu.studentmanagement.common.Result;
import com.liu.studentmanagement.entity.Clazz;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clazz")
@CrossOrigin
@Tag(name = "班级管理模块", description = "负责班级的增删改查")
public class ClazzController {
    @Autowired
    private IClazzService clazzService;

    @PostMapping("/add")
    @Operation(summary = "新增班级") // 🌟 描述这个接口
    public Result<?> add(@RequestBody @Validated Clazz clazz){
        clazzService.save(clazz);
        return Result.success(null);
    }


    /**
     * 分页查询
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询班级列表") // 🌟 描述这个接口
    public Result<PageResult<Clazz>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String className) {

        // 1. 准备 MP 的分页参数
        Page<Clazz> pageParam = new Page<>(pageNum, pageSize);

        // 2. 构建查询条件
        LambdaQueryWrapper<Clazz> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(className != null, Clazz::getClassName, className);


        // 3. 执行查询
        IPage<Clazz> mpPage = clazzService.page(pageParam, wrapper);
        PageResult<Clazz> finalResult = new PageResult<>(
                mpPage.getRecords(),
                mpPage.getTotal()
        );
        return Result.success(finalResult);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}") // 稍微规范一点，用 @DeleteMapping
    @Operation(summary = "删除班级") // 🌟 描述这个接口
    public Result<?> delete(@PathVariable Integer id) {
        clazzService.removeById(id);
        return Result.success(null);
    }

    @PutMapping("/update")
    @Operation(summary = "更新班级信息") // 🌟 描述这个接口
    public Result<?> update(@RequestBody @Validated Clazz clazz) {
        clazzService.updateById(clazz);
        return Result.success(null);
    }




}
