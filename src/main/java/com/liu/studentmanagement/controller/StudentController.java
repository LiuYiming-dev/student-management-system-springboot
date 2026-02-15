package com.liu.studentmanagement.controller;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.studentmanagement.common.listener.StudentImportListener;
import com.liu.studentmanagement.entity.vo.StudentExcelVO;
import com.liu.studentmanagement.service.studentService.IStudentService;
import com.liu.studentmanagement.common.Result;
import com.liu.studentmanagement.entity.Student;
import com.liu.studentmanagement.entity.dto.StudentDTO;
import com.liu.studentmanagement.entity.vo.StudentVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@RestController // 表示返回的是数据不是页面
@RequestMapping("/student") // 统一前缀
@CrossOrigin // 🌟重要！允许跨域，为了以后Vue能访问
@Tag(name = "学生管理模块", description = "负责学生的增删改查") // 🌟 描述这个 Controller
public class StudentController {

    private final IStudentService studentService;
    private final String uploadPath;

    public StudentController(IStudentService studentService, @Value("${file.upload-path}") String uploadPath) {
        this.studentService = studentService;
        this.uploadPath = uploadPath;
    }

    /**
     * 添加学生行
     */
    @PostMapping("/add")
    @Operation(summary = "新增学生") // 🌟 描述这个接口
    public Result<?> add(@RequestBody @Validated StudentDTO studentDTO) {
        studentService.addStudent(studentDTO);
        return Result.success(null);
    }


    /**
     * 分页查询
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询学生列表(含班级信息)")
    public Result<Page<StudentVO>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name) {

        Page<StudentVO> voPage = studentService.getStudentVOPage(pageNum, pageSize, name);
        return Result.success(voPage);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}") // 稍微规范一点，用 @DeleteMapping
    @Operation(summary = "删除学生") // 🌟 描述这个接口
    public Result<?> delete(@PathVariable Integer id) {
        studentService.deleteStudent(id);
        return Result.success(null);
    }

    @PutMapping("/update")
    @Operation(summary = "更新学生信息") // 🌟 描述这个接口
    public Result<?> update(@RequestBody @Validated StudentDTO studentDTO) {
        studentService.updateStudent(studentDTO);
        return Result.success(null);
    }

    @GetMapping("/search/{id}")
    @Operation(summary = "查找学生信息通过id")
    public Result<?> searchStudentClassById(@PathVariable Integer id) {
        Student student = studentService.getById(id);
        if (student == null) {
            return Result.error("404", "找不到该学生");
        }
        return Result.success(student);
    }

    @GetMapping("/export")
    @Operation(summary = "导出学生信息")
    public void exportStudent(HttpServletResponse response) {
        studentService.exportStudent(response);
    }


    @PostMapping("/import")
    @Operation(summary = "从Excel导入学生")
    public Result<?> importStudent(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), StudentExcelVO.class, new StudentImportListener(studentService::importStudentExcel)).sheet().doRead();

        return Result.success(null);
    }

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        // 1. 定义存储路径
        File dir = new File(uploadPath);
        if (!dir.exists()) dir.mkdirs();

        // 2. 生成新文件名
        String fileName = UUID.randomUUID() + ".jpg";

        // 3. 保存文件
        file.transferTo(new File(uploadPath + fileName));

        // 4. 🌟 返回虚拟路径（对应 WebConfig 里的映射）
        String url = "http://localhost:8080/images/" + fileName;
        return Result.success(url);
    }
}