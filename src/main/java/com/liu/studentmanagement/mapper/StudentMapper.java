package com.liu.studentmanagement.mapper;
import com.liu.studentmanagement.entity.Student;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface StudentMapper {

    // 查询所有
    @Select("SELECT * FROM student")
    List<Student> findAll();

    // 根据ID查询
    @Select("SELECT * FROM student WHERE id = #{id}")
    Student findById(Integer id);

    // 新增
    @Insert("INSERT INTO student(student_no, name, age, email) VALUES(#{studentNo}, #{name}, #{age}, #{email})")
    int insert(Student student);

    // 删除
    @Delete("DELETE FROM student WHERE id = #{id}")
    int deleteById(Integer id);

    // 修改
    @Update("UPDATE student SET name=#{name}, age=#{age}, email=#{email} WHERE id=#{id}")
    int update(Student student);

    // 这里的 SQL 意思是：如果 name 传了，就拼 LIKE；没传就查所有
    @Select("SELECT * FROM student WHERE name LIKE concat('%', #{name}, '%') LIMIT #{offset}, #{pageSize}")
    List<Student> findByPage(@Param("name") String name,
                             @Param("offset") Integer offset,
                             @Param("pageSize") Integer pageSize);

    // 还需要一个查总条数的方法，告诉前端一共有多少页
    @Select("SELECT count(*) FROM student WHERE name LIKE concat('%', #{name}, '%')")
    Integer count(@Param("name") String name);
}