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
    ;

    private String errorCode;

    private String errorMessage;
}
