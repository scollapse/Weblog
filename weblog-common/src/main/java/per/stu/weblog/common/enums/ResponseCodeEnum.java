package per.stu.weblog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import per.stu.weblog.common.excption.BaseExceptionInterface;

/**
 * Response code enum
 * @author Stu
 * @date 2021/07/16
 *
 */
@Getter
@AllArgsConstructor
public enum  ResponseCodeEnum implements BaseExceptionInterface {

    // 通用异常状态码
    SYSTEM_ERROR("10000","系统异常"),

    //参数异常状态码
    PARAM_ERROR("10001","参数异常"),

    //业务异常状态码
    PRODUCT_NOT_FOUND("20001","商品不存在"),

    LOGIN_FAIL("20000", "登录失败"),

    USERNAME_OR_PWD_ERROR("20001", "用户名或密码错误"),

    UNAUTHORIZED("20002", "无权限访问，请先登录"),

    ACCESS_DENIED("20003","账号权限不足" );

    private String errorCode;

    private String errorMessage;
}
