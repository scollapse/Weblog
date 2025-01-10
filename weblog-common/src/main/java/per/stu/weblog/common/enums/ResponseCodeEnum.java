package per.stu.weblog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import per.stu.weblog.common.excption.BaseExceptionInterface;

/**
 * Response code enum
 *
 * @author Stu
 * @date 2021/07/16
 */
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseExceptionInterface {

    // 通用异常状态码
    SYSTEM_ERROR("10000", "系统异常"),

    //参数异常状态码
    PARAM_ERROR("10001", "参数异常"),

    //业务异常状态码
    PRODUCT_NOT_FOUND("20001", "商品不存在"),

    LOGIN_FAIL("20000", "登录失败"),

    USERNAME_OR_PWD_ERROR("20001", "用户名或密码错误"),

    UNAUTHORIZED("20002", "无权限访问，请先登录"),

    ACCESS_DENIED("20003", "账号权限不足"),

    UPDATE_PASSWORD_FAILED("20004", "修改密码失败"),

    USER_NOT_FOUND("20005", "用户不存在"),

    USER_ALREADY_EXIST("20006", "用户已存在"),

    USER_ROLE_NOT_EXIST("20007", "用户角色不存在"),

    USER_ROLE_ALREADY_EXIST("20008", "用户角色已存在"),

    USER_ROLE_CANNOT_DELETE("20009", "用户角色不能删除"),

    USER_ROLE_CANNOT_UPDATE("20010", "用户角色不能更新"),

    USER_ROLE_CANNOT_CREATE("20011", "用户角色不能创建"),

    CATEGORY_NAME_EXIST( "20012", "分类名称已存在,请勿重复创建"),

    CATEGORY_ADD_ERROR("20013","分类添加失败" ),

    CATEGORY_IS_NOT_EXIST( "20014", "分类不存在"),

    TAG_IS_NOT_EXIST( "20015", "标签不存在") ,


    FILE_UPLOAD_FAILED( "20016", "文件上传失败"),
    CATEGORY_NOT_EXISTED( "20017", "分类不存在"),
    ARTICLE_NOT_FOUND( "20018", "文章不存在"),;

    private String errorCode;

    private String errorMessage;
}
