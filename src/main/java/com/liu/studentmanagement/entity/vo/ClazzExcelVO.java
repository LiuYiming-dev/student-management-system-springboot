package com.liu.studentmanagement.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data

public class ClazzExcelVO {
    @ExcelProperty(value = "id")
    private Integer id;

    @ExcelProperty(value = "className")
    private String className;

    @ExcelProperty(value = "teacherName")
    private String teacherName;

    @ExcelProperty(value = "phoneNumber")
    private String phoneNumber;
}
