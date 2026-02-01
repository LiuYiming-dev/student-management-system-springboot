package com.liu.studentmanagement;


import com.liu.studentmanagement.entity.Student;
import com.liu.studentmanagement.mapper.StudentMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class StudentMapperTest {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- 开始测试 MyBatis-Plus 查询所有学生 ------"));
        // selectList(null) 表示没有查询条件，即查询所有
        List<Student> list = studentMapper.selectList(null);
        Assertions.assertEquals(14, list.size());
        // 打印结果
        list.forEach(System.out::println);
    }
}
