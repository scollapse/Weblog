package per.stu.weblog.admin.model.vo.article;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.NotNull;

/**
 * @description:  删除文章 VO
 * @author: syl
 * @create: 2025-01-10 17:11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "删除文章 VO")
public class DeleteArticleReqVO {

    @NotNull(message = "文章 ID 不能为空")
    private Long id;
}

