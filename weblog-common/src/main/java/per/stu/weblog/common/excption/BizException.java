package per.stu.weblog.common.excption;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常类
 * @author Stu
 * @date 2021/07/16 16:22
 *
 */
@Getter
@Setter
public class BizException extends RuntimeException {

    // 异常码
    private String errorCode;

    private String errorMessage;

    public BizException(BaseExceptionInterface baseExceptionInterface) {
        this.errorCode = baseExceptionInterface.getErrorCode();
        this.errorMessage = baseExceptionInterface.getErrorMessage();
    }

}
