package per.stu.weblog.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import per.stu.weblog.common.utils.JsonUtil;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ApiOperationLog 的切面类
 *
 * @author babax
 * @version v1.0
 * @date 2024/12/13 10:30
 * @modified by
 */
@Aspect
@Component
@Slf4j
public class ApiOperationLogAspect {

    /** 以自定义 @ApiOperationLog 注解为切点，凡是添加 @ApiOperationLog 的方法，都会执行环绕中的代码 */
    @Pointcut("@annotation(per.stu.weblog.common.aspect.ApiOperationLog)")
    public  void apiOperationLog() {}

    /** 环绕通知，在目标方法执行前后，打印日志 */
    @Around("apiOperationLog()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // 请求开始时间
            long startTime = System.currentTimeMillis();
            // MDC
            MDC.put("traceId", UUID.randomUUID().toString());

            // 获取请求的类名、方法名、参数
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] args = joinPoint.getArgs();
            // 入参转 JSON 字符串
            String argsJsonStr = Arrays.stream(args).map(toJsonStr()).collect(Collectors.joining(", "));
            // 功能描述信息
            String description = getApiOperationLogDescription(joinPoint);
            // 打印请求相关参数
            log.info("====== 请求开始: [{}], 入参: {}, 请求类: {}, 请求方法: {} =================================== ",
                    description, argsJsonStr, className, methodName);
            // 执行目标方法
            Object result = joinPoint.proceed();
            // 执行耗时
            long endTime = System.currentTimeMillis();
            long executeTime = endTime - startTime;
            // 打印请求结果
            log.info("====== 请求结束: [{}], 出参: {}, 耗时: {}ms ======= ", description, JsonUtil.toJsonString(result), executeTime);
            return result;
        }finally {
            MDC.clear();
        }
    }

    /**
     * 获取功能描述信息
     * @param joinPoint
     * @return
     */
    private String getApiOperationLogDescription(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        return signature.getMethod().getAnnotation(ApiOperationLog.class).description();
    }


    /**
     * 转 JSON 字符串
     * @return
     */
    private Function<Object, String> toJsonStr() {
        return arg -> JsonUtil.toJsonString(arg);
    }
}
