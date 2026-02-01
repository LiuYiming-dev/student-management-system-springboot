package com.liu.studentmanagement.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.studentmanagement.entity.Student;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {


}