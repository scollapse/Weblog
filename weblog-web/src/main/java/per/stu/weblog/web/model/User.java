package per.stu.weblog.web.model;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * 测试 用户实体类
 *
 * @author babax
 * @version v1.0
 * @date 2024/12/13 10:51
 * @modified by
 */
@Data
public class User {

    // 用户姓名
    @NotBlank(message = "用户名不能为空")
    private String username;

    // 用户密码
    @NotBlank(message = "密码不能为空")
    private String password;

    // 用户邮箱
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    // 年龄
    @NotNull(message = "年龄不能为空")
    @Min(value = 18, message = "年龄必须大于等于18")
    @Max(value = 100, message = "年龄必须小于等于100")
    private Integer age;
}
