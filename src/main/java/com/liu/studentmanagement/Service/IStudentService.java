package com.liu.studentmanagement.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.studentmanagement.entity.Student;

public interface IStudentService extends IService<Student> {
    void addStudent(Student student);
    void updateStudent(Student student);
    void deleteStudent(Integer id);
}