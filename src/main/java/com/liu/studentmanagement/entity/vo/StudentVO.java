package com.liu.studentmanagement.entity.vo;

import com.liu.studentmanagement.entity.Student;
import lombok.Data;
import lombok.EqualsAndHashCode;



@EqualsAndHashCode(callSuper = true)
@Data
public class StudentVO extends Student {
    private String className;
}
