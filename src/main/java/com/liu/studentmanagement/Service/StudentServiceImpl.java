package com.liu.studentmanagement.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.studentmanagement.entity.Clazz;
import com.liu.studentmanagement.entity.Student;
import com.liu.studentmanagement.entity.vo.StudentVO;
import com.liu.studentmanagement.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
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
    public Page<StudentVO> getStudentVOPage(Integer pageNum, Integer pageSize, String name) {
        Page<StudentVO> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectStudentPage(page, name);
    }

    @Override
    public void updateStudent(Student student) {
        if (!this.updateById(student)) {
            throw new RuntimeException("修改失败，ID不存在");
        }
    }
    @Override
    public void addStudent(Student student) {
        // 使用 log.info 记录关键业务信息
        // 使用 {} 占位符，这是 SLF4J 的标准写法，效率高且优雅
        log.info("准备添加学生，学号：{}, 姓名：{}", student.getStudentNo(), student.getName());

        this.save(student);
        log.info("学生添加成功，数据库分配ID：{}", student.getId());
    }

}