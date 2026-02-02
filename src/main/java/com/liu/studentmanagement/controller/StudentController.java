package com.liu.studentmanagement.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.studentmanagement.Service.StudentServiceImpl;
import com.liu.studentmanagement.common.PageResult;
import com.liu.studentmanagement.common.Result;
import com.liu.studentmanagement.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController // 表示返回的是数据不是页面
@RequestMapping("/student") // 统一前缀
@CrossOrigin // 🌟重要！允许跨域，为了以后Vue能访问
public class StudentController {

    @Autowired
    private StudentServiceImpl studentService;
    @Autowired
    private Mapper mapper;


    /**
     * 添加学生行
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody @Validated Student student) {
        studentService.save(student);
        return Result.success(null);
    }



    /**
     * 分页查询
     */
    @GetMapping("/page")
    public Result<PageResult<Student>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name) {

        // 1. 准备 MP 的分页参数
        Page<Student> pageParam = new Page<>(pageNum, pageSize);

        // 2. 构建查询条件
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null, Student::getName, name);


        // 3. 执行查询
        IPage<Student> mpPage = studentService.page(pageParam, wrapper);
        PageResult<Student> finalResult = new PageResult<>(
                mpPage.getRecords(),
                mpPage.getTotal()
        );
        return Result.success(finalResult);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}") // 稍微规范一点，用 @DeleteMapping
    public Result<?> delete(@PathVariable Integer id) {
        studentService.removeById(id);
        return Result.success(null);
    }

    @PutMapping("/update")
    public Result<?> update(@RequestBody @Validated Student student) {
        studentService.updateById(student);
        return Result.success(null);
    }







}