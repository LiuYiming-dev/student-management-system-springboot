package com.liu.studentmanagement.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.studentmanagement.entity.Student;
import com.liu.studentmanagement.mapper.StudentMapper;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {



    private Student ensureStudentExists(Integer id) {
        Student student = this.getById(id);
        if (student == null) {
            throw new RuntimeException("操作失败：学号为 " + id + " 的学生不存在！");
        }
        return student;
    }

    public void deleteStudent(Integer id) {
        ensureStudentExists(id);
        this.removeById(id);
    }

    public void updateStudent(Student student) {
        ensureStudentExists(student.getId());
        this.updateById(student);
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