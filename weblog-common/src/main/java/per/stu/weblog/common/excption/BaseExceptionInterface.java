package per.stu.weblog.common.excption;

/**
 * Base exception interface
 * @author Stu
 * @date 2021/05/08
 * @since 1.0.0
 * @see per.stu.weblog.common.excption.BaseExceptionInterface
 */
public interface BaseExceptionInterface {
    String getErrorCode();
    String getErrorMessage();
}
