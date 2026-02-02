package com.liu.studentmanagement.config;
import java.util.stream.Collectors;
import com.liu.studentmanagement.common.Result;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 1. @RestControllerAdvice = @ControllerAdvice + @ResponseBody
// 意思是：这个类是所有 Controller 的“统一建议/增强”，且返回的是 JSON 数据
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 2. @ExceptionHandler 指定要捕获哪个异常类
    // 这里我们捕获 Exception.class，也就是所有的错误都能抓到
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        // 在后台打印错误日志，方便你自己排查
        e.printStackTrace();

        // 返回给前端的统一格式
        // 这里的 code 500 表示服务器内部错误
        return Result.error("500", "系统异常：" + e.getMessage());
    }

    // 🌟 新增：专门处理参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidationException(MethodArgumentNotValidException e) {
        // 从异常中提取出具体的错误信息
        BindingResult bindingResult = e.getBindingResult();
        String message = bindingResult.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(";"));
        // 返回 400 状态码，表示请求参数有问题
        return Result.error("400", message);
    }


    @ExceptionHandler(DuplicateKeyException.class)
    public Result<?> handleDuplicateKeyException(DuplicateKeyException e) {
        return Result.error("400", "数据重复：该学号或ID已存在，请检查！");
    }




}