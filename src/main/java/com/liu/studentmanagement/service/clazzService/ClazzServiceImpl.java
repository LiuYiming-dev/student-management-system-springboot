package com.liu.studentmanagement.service.clazzService;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.studentmanagement.entity.Clazz;
import com.liu.studentmanagement.mapper.ClazzMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements IClazzService {

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
