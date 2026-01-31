package com.liu.studentmanagement.Service;
import com.liu.studentmanagement.common.PageResult;
import com.liu.studentmanagement.entity.Student;
import com.liu.studentmanagement.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 🌟 别忘了贴这个服务员标签
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public PageResult<Student> getStudentPage(Integer pageNum, Integer pageSize, String name) {
        if (name == null) {
            name = "";
        }

        // 计算 offset
        int offset = (pageNum - 1) * pageSize;

        // 查数据
        List<Student> list = studentMapper.findByPage(name, offset, pageSize);
        // 查总数
        Integer total = studentMapper.count(name);

        // 返回包装好的对象
        return new PageResult<>(list, Long.valueOf(total));
    }


    public void addStudent(Student student) {
        // 业务逻辑：如果没填学号，自动生成一个
        if (student.getStudentNo() == null || student.getStudentNo().isEmpty()) {
            student.setStudentNo("S" + System.currentTimeMillis());
        }

        // 业务逻辑：如果名字太长，抛出异常（模拟校验）
        if (student.getName().length() > 10) {
            throw new RuntimeException("名字太长啦！");
            // 抛出异常后，Spring Boot默认会返回500错误，后续可以用全局异常处理器拦截
        }

        // 调用 Mapper 存库
        studentMapper.insert(student);
    }

    // 3. 删除逻辑
    public void deleteStudent(Integer id) {
        // 这里可以加业务逻辑，比如：如果是管理员账号不能删？
        if (id == 1) {
            throw new RuntimeException("老板的账号不能删！");
        }
        studentMapper.deleteById(id);
    }

    // 4. 修改逻辑
    public void updateStudent(Student student) {
        // 业务校验：ID不能为空
        if (student.getId() == null) {
            throw new RuntimeException("修改必须带ID");
        }
        studentMapper.update(student);
    }


}
