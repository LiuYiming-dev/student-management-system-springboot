package com.liu.studentmanagement.common.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.liu.studentmanagement.entity.vo.StudentExcelVO;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class StudentImportListener extends AnalysisEventListener<StudentExcelVO> {
    private final List<StudentExcelVO> list = new ArrayList<>();
    private final Consumer<List<StudentExcelVO>> batchConsumer;

    public StudentImportListener(Consumer<List<StudentExcelVO>> batchConsumer) {
        this.batchConsumer = batchConsumer;
    }

    @Override
    public void invoke(StudentExcelVO data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        batchConsumer.accept(list);
    }
}