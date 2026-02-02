package com.liu.studentmanagement.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.studentmanagement.entity.Student;
import com.liu.studentmanagement.mapper.StudentMapper;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {


    public boolean updateStudent(Student student) {
        // 1. 调用 MP 的 updateById
        boolean success = this.updateById(student);

        // 2. 🌟 关键点：检查返回值
        // 如果 success 是 false，说明数据库里没这个 ID
        if (!success) {
            // 手动抛出异常，让 GlobalExceptionHandler 捕获它
            throw new RuntimeException("修改失败，学生ID不存在！");
        }

        return true;
    }

    public void addStudent(Student student) {
        // 1. 检查学号是否存在
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getStudentNo, student.getStudentNo());

        long count = this.count(wrapper);
        if (count > 0) {
            throw new RuntimeException("学号 " + student.getStudentNo() + " 已经被占用了！");
        }

        // 2. 存入数据库
        this.save(student);
    }
}