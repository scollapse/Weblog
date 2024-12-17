package per.stu.weblog.common.utils;

import lombok.Data;
import per.stu.weblog.common.excption.BaseExceptionInterface;
import per.stu.weblog.common.excption.BizException;

import java.io.Serializable;

/**
 * @author Stu
 * @date 2021/2/24 16:30
 * @description 响应参数工具类
 *
 */
@Data
public class Response<T> implements Serializable {

    // 默认成功
    private boolean success = true;

    // 错误信息
    private String errorMessage;

    // 错误码
    private String errorCode;

    // 响应数据
    private T data;

    // 成功相应
    public static <T> Response<T> success() {
        Response<T> response = new Response<>();
        return response;
    }
    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        return response;
    }

    // 失败相应
    public static <T> Response<T> fail() {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        return response;
    }
    public static <T> Response<T> fail(String message) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setErrorMessage(message);
        return response;
    }
    public static <T> Response<T> fail(String message, String errorCode) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setErrorMessage(message);
        response.setErrorCode(errorCode);
        return response;
    }
    public static <T> Response<T> fail(BizException e) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setErrorMessage(e.getErrorMessage());
        response.setErrorCode(e.getErrorCode());
        return response;
    }
    public static <T> Response<T> fail(BaseExceptionInterface baseExceptionInterface) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setErrorMessage(baseExceptionInterface.getErrorMessage());
        response.setErrorCode(baseExceptionInterface.getErrorCode());
        return response;
    }
}
