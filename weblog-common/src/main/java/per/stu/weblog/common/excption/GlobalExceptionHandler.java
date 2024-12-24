package per.stu.weblog.common.excption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import per.stu.weblog.common.enums.ResponseCodeEnum;
import per.stu.weblog.common.utils.Response;
import org.springframework.security.access.AccessDeniedException;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author Stu
 * @date 2021/2/24 16:20
 * @description 全局异常处理器
 *
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 全局异常处理器
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public Response<Object> handleBizException(HttpServletRequest request, BizException e) {
        log.warn("{} request fail, errorCode: {}, errorMessage: {}", request.getRequestURI(), e.getErrorCode(), e.getErrorMessage());
        return Response.fail(e);
    }

    /**
     * 全局异常处理器
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response<Object> handleException(HttpServletRequest request, Exception e) {
        log.error("{} request fail, errorMessage: {}", request.getRequestURI(), e.getMessage());
        return Response.fail(ResponseCodeEnum.SYSTEM_ERROR);
    }


    /**
     *  参数校验异常处理器
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Response<Object> handleException(HttpServletRequest request, MethodArgumentNotValidException e) {
        String errCode = ResponseCodeEnum.PARAM_ERROR.getErrorCode();
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sb = new StringBuilder();
        // 获取校验失败的字段，并组合成错误信息 格式：字段名:错误信息
        Optional.ofNullable(bindingResult.getFieldErrors()).ifPresent(fieldErrors -> {
            fieldErrors.forEach(fieldError -> {
                sb.append(fieldError.getField())
                        .append(":")
                        .append(fieldError.getDefaultMessage())
                        .append(",当前值：")
                        .append(fieldError.getRejectedValue())
                        .append(";");
            });
        });

        // 错误信息
        String errorMessage = sb.toString();
        log.warn("{} request fail, errorCode: {}, errorMessage: {}", request.getRequestURI(), errCode, errorMessage);
        return Response.fail(errCode, errorMessage);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public void throwAccessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        // 捕获到鉴权失败异常，主动抛出，交给 RestAccessDeniedHandler 去处理
        log.info("============= 捕获到 AccessDeniedException");
        throw e;
    }
}
