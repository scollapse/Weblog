package per.stu.weblog.admin.model.vo.blogsettings;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

/**
 * @description: 博客基础信息修改 VO
 * @author: syl
 * @create: 2025-01-08 09:51
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = " 博客基础信息修改 VO")
public class UpdateBlogSettingsReqVO {

    private String logo;

    @NotBlank(message = "博客名称不能为空")
    private String name;

    @NotBlank(message = "博客作者不能为空")
    private String author;

    @NotBlank(message = "博客介绍语不能为空")
    private String introduction;

    @NotBlank(message = "博客头像不能为空")
    private String avatar;

    private String githubHomepage;

    private String csdnHomepage;

    private String giteeHomepage;

    private String zhihuHomepage;
}

