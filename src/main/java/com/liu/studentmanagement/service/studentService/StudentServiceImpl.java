package com.liu.studentmanagement.service.studentService;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.studentmanagement.common.BaseContext;
import com.liu.studentmanagement.common.enums.GenderEnum;
import com.liu.studentmanagement.entity.Student;
import com.liu.studentmanagement.entity.dto.StudentDTO;
import com.liu.studentmanagement.entity.vo.DashboardVO;
import com.liu.studentmanagement.entity.vo.StudentVO;
import com.liu.studentmanagement.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

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
    public DashboardVO getDashboardStats() {
        DashboardVO dashboardVO = new DashboardVO();
        dashboardVO.setTotalStudents(this.count(new LambdaQueryWrapper<Student>().eq(Student::getDeleted, 0)));
        dashboardVO.setGenderData(baseMapper.countByGender());
        dashboardVO.setClassData(baseMapper.countByClass());
        return dashboardVO;
    }

    @Override
    public void updateStudent(StudentDTO studentDTO) {
        Student student = new Student();
        BeanUtils.copyProperties(studentDTO, student);
        if (studentDTO.getGender() != null) {
            student.setGender(GenderEnum.getByCode(studentDTO.getGender()));
        }
        if (!this.updateById(student)) {
            throw new RuntimeException("修改失败，ID不存在");
        }
    }
    @Override
    public void addStudent(StudentDTO studentDTO) {
        // 使用 log.info 记录关键业务信息
        // 使用 {} 占位符，这是 SLF4J 的标准写法，效率高且优雅
        Integer currentOperatorId = BaseContext.getCurrentId();
        log.info("当前操作人ID是: {}", currentOperatorId);
        Student student = new Student();
        BeanUtils.copyProperties(studentDTO, student);
        if (studentDTO.getGender() != null) {
            student.setGender(GenderEnum.getByCode(studentDTO.getGender()));
        }

        log.info("准备添加学生，学号：{}, 姓名：{}", student.getStudentNo(), student.getName());
        log.info(student.toString());
        if (student.getGender() == GenderEnum.MALE) log.info("正在录入一名男同学");
        else log.info("正在录入一名女同学");

        this.save(student);
        log.info("学生添加成功，数据库分配ID：{}", student.getId());
    }



}