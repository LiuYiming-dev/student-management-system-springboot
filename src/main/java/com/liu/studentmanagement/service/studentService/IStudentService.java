package com.liu.studentmanagement.service.studentService;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.studentmanagement.entity.Student;
import com.liu.studentmanagement.entity.dto.StudentDTO;
import com.liu.studentmanagement.entity.vo.DashboardVO;
import com.liu.studentmanagement.entity.vo.StudentVO;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IStudentService extends IService<Student> {
    void addStudent(StudentDTO studentDTO);
    void updateStudent(StudentDTO studentDTO);
    void deleteStudent(Integer id);
    Page<StudentVO> getStudentVOPage(Integer pageNum, Integer pageSize, String name);
    DashboardVO getDashboardStats();

    void exportStudent(HttpServletResponse response) throws IOException;
}
