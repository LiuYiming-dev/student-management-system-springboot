package com.liu.studentmanagement.service.studentService;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.studentmanagement.common.BaseContext;
import com.liu.studentmanagement.common.annotation.AutoLog;
import com.liu.studentmanagement.common.enums.GenderEnum;
import com.liu.studentmanagement.entity.Clazz;
import com.liu.studentmanagement.entity.Student;
import com.liu.studentmanagement.entity.dto.StudentDTO;
import com.liu.studentmanagement.entity.vo.DashboardVO;
import com.liu.studentmanagement.entity.vo.StudentExcelVO;
import com.liu.studentmanagement.entity.vo.StudentVO;
import com.liu.studentmanagement.mapper.StudentMapper;
import com.liu.studentmanagement.service.clazzService.IClazzService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {
    private final IClazzService classService;

    public StudentServiceImpl(IClazzService classService) {
        this.classService = classService;
    }

    @AutoLog(value = "学生模块", action = "删除学生")
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
        dashboardVO.setTotalClasses(classService.count());
        dashboardVO.setAvgAge(baseMapper.averageAge());
        dashboardVO.setGenderData(baseMapper.countByGender());
        dashboardVO.setClassData(baseMapper.countByClass());
        return dashboardVO;
    }

        @Override
        public void exportStudent(HttpServletResponse response) {
            try{
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.setCharacterEncoding("utf-8");
                String fileName = URLEncoder.encode("students", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
                response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

                List<StudentExcelVO> list = baseMapper.selectAllStudents();
                EasyExcel.write(response.getOutputStream(), StudentExcelVO.class).sheet("students").doWrite(list);
            }catch (Exception e){
                log.error("导出学生Excel失败", e);
                throw new RuntimeException("导出失败：" + e.getMessage());
            }
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

    public List<StudentExcelVO> selectAllStudents() {
        return baseMapper.selectAllStudents();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importStudentExcel(List<StudentExcelVO> list) {
        Map<String, Integer> classMap = classService.list().stream()
                .collect(Collectors.toMap(Clazz::getClassName, Clazz::getId));

        List<Student> students = list.stream().map(vo -> {
            Student student = new Student();
            BeanUtils.copyProperties(vo, student);

            Integer clazzId = classMap.get(vo.getClassName());
            if (clazzId == null) {
                throw new RuntimeException("导入失败：找不到名为 [" + vo.getClassName() + "] 的班级，请检查数据库");
            }

            student.setClazzId(clazzId);
            return student;
        }).toList();
        this.saveBatch(students);
    }


}