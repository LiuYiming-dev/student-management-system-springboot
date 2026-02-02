package com.liu.studentmanagement.entity.vo;

import com.liu.studentmanagement.entity.Student;
import lombok.Data;


@Data
public class StudentVO extends Student {
    private String className;
    private String teacherName;
}
