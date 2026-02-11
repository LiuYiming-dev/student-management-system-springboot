package com.liu.studentmanagement.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.liu.studentmanagement.common.enums.GenderEnum;
import com.liu.studentmanagement.common.handler.GenderConverter;
import lombok.Data; // Lombok注解


@Data
public class StudentExcelVO {


    @ExcelProperty(value = "学号", index = 0)
    private String studentNo;
    @ExcelProperty(value = "姓名", index = 1)
    private String name;
    @ExcelProperty(value = "性别", index = 2, converter = GenderConverter.class)
    private GenderEnum gender;

    @ExcelProperty(value = "班级", index = 3)
    private String className;
    @ExcelProperty(value = "年龄", index = 4)
    private Integer age;
    @ExcelProperty(value = "电话号码", index = 5)
    private String phoneNumber;


}