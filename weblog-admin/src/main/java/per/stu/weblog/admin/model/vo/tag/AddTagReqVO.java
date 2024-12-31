package per.stu.weblog.admin.model.vo.tag;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @description:  AddTagReqVO
 * @author: syl
 * @create: 2024-12-31 16:25
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "添加标签 VO")
public class AddTagReqVO {

    @NotNull
    List<String> tagNames;
}
