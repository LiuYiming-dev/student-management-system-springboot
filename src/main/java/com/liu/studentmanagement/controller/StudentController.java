package com.liu.studentmanagement.controller;
import com.liu.studentmanagement.Service.StudentService;
import com.liu.studentmanagement.common.PageResult;
import com.liu.studentmanagement.common.Result;
import com.liu.studentmanagement.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController // 表示返回的是数据不是页面
@RequestMapping("/student") // 统一前缀
@CrossOrigin // 🌟重要！允许跨域，为了以后Vue能访问
public class StudentController {

    @Autowired
    private StudentService studentService;


    /**
     * 添加学生行
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody Student student) {
        studentService.addStudent(student); // 脏活累活交给 Service
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

        // 直接调 Service，不管怎么算的
        PageResult<Student> pageResult = studentService.getStudentPage(pageNum, pageSize, name);

        return Result.success(pageResult);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}") // 稍微规范一点，用 @DeleteMapping
    public Result<?> delete(@PathVariable Integer id) {
        studentService.deleteStudent(id);
        return Result.success(null);
    }

    @PutMapping("/update")
    public Result<?> update(@RequestBody Student student) {
        studentService.updateStudent(student);
        return Result.success(null);
    }







}