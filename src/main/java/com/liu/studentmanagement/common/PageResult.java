package com.liu.studentmanagement.common;
import lombok.Data;
import java.util.List;

@Data
public class PageResult<T> {
    private List<T> list; // 数据列表
    private Long total;   // 总记录数

    public PageResult(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }
}