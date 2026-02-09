package com.liu.studentmanagement.entity.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DashboardVO {
    private Long totalStudents; // 总人数
    private Long totalClasses;  // 🌟 新增：总班级数
    private Double avgAge;      // 🌟 新增：平均年龄
    private List<Map<String, Object>> genderData; // 性别分布 [ {name: '男', value: 10}, {name: '女', value: 8} ]
    private List<Map<String, Object>> classData;  // 班级分布 [ {name: '计算机一班', value: 30}, ... ]
}