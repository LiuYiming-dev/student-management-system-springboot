package com.liu.studentmanagement.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.studentmanagement.entity.Student;
import com.liu.studentmanagement.entity.vo.StudentVO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;


@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    Page<StudentVO> selectStudentPage(Page<StudentVO> page, @Param("name") String name);
    List<Map<String, Object>> countByGender();
    List<Map<String, Object>> countByClass();
    Double averageAge();
}