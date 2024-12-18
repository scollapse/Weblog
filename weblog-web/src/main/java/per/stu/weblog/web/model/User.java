package per.stu.weblog.web.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 测试 用户实体类
 *
 * @author babax
 * @version v1.0
 * @date 2024/12/13 10:51
 * @modified by
 */
@Data
@ApiModel(value = "用户实体类")
public class User {

    // 用户姓名
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    // 用户密码
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    // 用户邮箱
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    // 年龄
    @NotNull(message = "年龄不能为空")
    @Min(value = 18, message = "年龄必须大于等于18")
    @Max(value = 100, message = "年龄必须小于等于100")
    @ApiModelProperty(value = "年龄", required = true)
    private Integer age;

    // 创建时间
    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

    // 更新日期
    @ApiModelProperty(value = "更新日期", required = true)
    private LocalDate updateDate;

    // 时间
    @ApiModelProperty(value = "时间", required = true)
    private LocalTime time;
}
