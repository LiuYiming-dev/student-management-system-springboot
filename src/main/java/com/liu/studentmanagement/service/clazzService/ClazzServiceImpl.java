package com.liu.studentmanagement.service.clazzService;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.studentmanagement.entity.Clazz;
import com.liu.studentmanagement.entity.Student;
import com.liu.studentmanagement.mapper.ClazzMapper;
import com.liu.studentmanagement.service.studentService.IStudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements IClazzService {
//    @Autowired
//    private IStudentService studentService;
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void deleteClazzStudents(Integer clazzId) {
//        log.info("正在删除班级 ID 为 {} 的所有学生...", clazzId);
//        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Student::getClazzId, clazzId);
//        studentService.remove(queryWrapper);
//
//        log.error("系统突然崩溃了！");
//        int i = 1 / 0;
//
//        log.info("deleting clazzId{}", clazzId);
//        this.removeById(clazzId);
//    }

    @Override
    public Page<Clazz> getClazzPage(Integer pageNum, Integer pageSize) {
        Page<Clazz> pageParam = new Page<>(pageNum, pageSize);
        return this.page(pageParam);
    }

    @Override
    public List<Clazz> listAll() {
        return this.list();
    }
}
