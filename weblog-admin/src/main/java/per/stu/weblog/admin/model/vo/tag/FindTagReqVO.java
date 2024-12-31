package per.stu.weblog.admin.model.vo.tag;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "查询标签入参 VO")
public class FindTagReqVO {

    /**
     * 标签名称
     */
    @NotBlank(message = "标签名称不能为空")
    private String key;

}
