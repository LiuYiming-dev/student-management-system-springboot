package com.liu.studentmanagement.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.studentmanagement.entity.Student;
import com.liu.studentmanagement.mapper.StudentMapper;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    public void deleteStudent(Integer id) {
        if (!this.removeById(id)) {
            throw new RuntimeException("删除失败，ID不存在");
        }
    }

    public void updateStudent(Student student) {
        if (!this.updateById(student)) {
            throw new RuntimeException("修改失败，ID不存在");
        }
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