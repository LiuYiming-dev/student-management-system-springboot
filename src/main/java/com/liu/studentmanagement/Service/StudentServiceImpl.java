package com.liu.studentmanagement.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.studentmanagement.Service.IStudentService;
import com.liu.studentmanagement.entity.Student;
import com.liu.studentmanagement.mapper.StudentMapper;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}