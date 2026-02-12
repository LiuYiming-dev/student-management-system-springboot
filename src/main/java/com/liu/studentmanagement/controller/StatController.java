package com.liu.studentmanagement.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.liu.studentmanagement.common.Result;
import com.liu.studentmanagement.entity.Clazz;
import com.liu.studentmanagement.entity.vo.ClazzExcelVO;
import com.liu.studentmanagement.entity.vo.DashboardVO;
import com.liu.studentmanagement.entity.vo.StudentExcelVO;
import com.liu.studentmanagement.service.clazzService.IClazzService;
import com.liu.studentmanagement.service.studentService.IStudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/stat")
@Tag(name = "统计分析")
@CrossOrigin
@Slf4j
public class StatController {

    private final IStudentService studentService;
    private final IClazzService clazzService;

    public StatController(IStudentService studentService, IClazzService clazzService) {
        this.studentService = studentService;
        this.clazzService = clazzService;
    }

    @GetMapping("/dashboard")
    @Operation(summary = "获取首页概览数据")
    public Result<DashboardVO> getDashboard() {
        return Result.success(studentService.getDashboardStats());
    }

    @GetMapping("/export")
    @Operation(summary = "一键导出")
    public void export(HttpServletResponse response) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("info", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");


        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(response.getOutputStream()).build();
            WriteSheet writeSheet_clazz = EasyExcel.writerSheet(0, "class").head(ClazzExcelVO.class).build();
            List<Clazz> ClazzList = clazzService.list();
            List<ClazzExcelVO> clazzExcelVOList = new ArrayList<>();
            for (Clazz clazz : ClazzList) {
                ClazzExcelVO clazzExcelVO = new ClazzExcelVO();
                BeanUtils.copyProperties(clazz, clazzExcelVO);
                clazzExcelVOList.add(clazzExcelVO);
            }
            excelWriter.write(clazzExcelVOList, writeSheet_clazz);


            WriteSheet writeSheet_student = EasyExcel.writerSheet(1, "student").head(StudentExcelVO.class).build();
            List<StudentExcelVO> studentExcelVOList = studentService.selectAllStudents();
            excelWriter.write(studentExcelVOList, writeSheet_student);

        }catch (Exception e){
            log.error("一键导出错误{}", e.getMessage());
        }finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }

    }


}