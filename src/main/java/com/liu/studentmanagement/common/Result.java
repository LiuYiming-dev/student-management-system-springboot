package com.liu.studentmanagement.common;
import lombok.Data;

@Data
public class Result<T> {
    private String code;
    private String msg;
    private T data;

    // 成功的方法
    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.setCode("200");
        r.setMsg("操作成功");
        r.setData(data);
        return r;
    }


    public static <T> Result<T> error(String code, String msg) {
        Result<T> r = new Result<>();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }
}