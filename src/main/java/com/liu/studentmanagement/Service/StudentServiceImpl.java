package com.liu.studentmanagement.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.studentmanagement.entity.Clazz;
import com.liu.studentmanagement.entity.Student;
import com.liu.studentmanagement.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {
    @Autowired
    IClazzService clazzService;

    @Override
    public void deleteStudent(Integer id) {
        if (!this.removeById(id)) {
            throw new RuntimeException("删除失败，ID不存在");
        }
    }

    @Override
    public void updateStudent(Student student) {
        if (!this.updateById(student)) {
            throw new RuntimeException("修改失败，ID不存在");
        }
    }
    @Override
    public void addStudent(Student student) {
        if (student.getClazzId() != null) {
            Clazz clazz = clazzService.getById(student.getClazzId());
            if (clazz == null) {
                throw new RuntimeException("操作失败：班级 ID " + student.getClazzId() + " 不存在！");
            }
        }
        this.save(student);
    }

}