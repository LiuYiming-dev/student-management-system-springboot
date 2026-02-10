package com.liu.studentmanagement.service.clazzService;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.studentmanagement.entity.Clazz;

import java.util.List;

public interface IClazzService extends IService<Clazz> {

    Page<Clazz> getClazzPage(Integer pageNum, Integer pageSize);

    List<Clazz> listAll();

}
