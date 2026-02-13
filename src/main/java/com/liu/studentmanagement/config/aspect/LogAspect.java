package com.liu.studentmanagement.config.aspect;

import com.liu.studentmanagement.common.BaseContext;
import com.liu.studentmanagement.common.annotation.AutoLog;
import com.liu.studentmanagement.entity.SysOperationLog;
import com.liu.studentmanagement.mapper.OperationLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class LogAspect {

    private final OperationLogMapper logMapper; // 你需要自己生成对应的Mapper

    public LogAspect(OperationLogMapper logMapper) {
        this.logMapper = logMapper;
    }

    @Around("@annotation(autoLog)") // 只要贴了@AutoLog的都管
    public Object doAround(ProceedingJoinPoint joinPoint, AutoLog autoLog) throws Throwable {
        long beginTime = System.currentTimeMillis();

        Object result;
        String errorMsg = null;

        try {
            // 1. 执行原有的业务逻辑
            result = joinPoint.proceed();
            return result;
        } catch (Throwable e) {
            errorMsg = e.getMessage();
            throw e;
        } finally {
            // 2. 🌟 无论成功失败，都在最后记录日志
            long costTime = System.currentTimeMillis() - beginTime;
            saveLog(joinPoint, autoLog, errorMsg, costTime);
        }
    }

    private void saveLog(ProceedingJoinPoint joinPoint, AutoLog autoLog, String errorMsg, long costTime) {
        SysOperationLog opLog = new SysOperationLog();

        // 🌟 关键点：利用反射拿方法信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        opLog.setMethodName(signature.getDeclaringTypeName() + "." + signature.getName());

        // 🌟 关键点：利用 ThreadLocal 拿当前登录人
        opLog.setOperatorId(BaseContext.getCurrentId());

        opLog.setModule(autoLog.value());
        opLog.setType(autoLog.action());
        opLog.setOperationTime(LocalDateTime.now());
        opLog.setCostTime(costTime);
        opLog.setErrorMsg(errorMsg);

        // 🌟 关键点：将参数对象转为 JSON 存入数据库
        // 这里可以使用之前提到的 Jackson 库
        // opLog.setParams(JSONUtil.toJsonStr(joinPoint.getArgs()));

        logMapper.insert(opLog);
    }
}