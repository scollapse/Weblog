package per.stu.weblog.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.stu.weblog.admin.model.vo.tag.AddTagReqVO;
import per.stu.weblog.admin.model.vo.tag.DeleteTagReqVO;
import per.stu.weblog.admin.model.vo.tag.FindTagPageListReqVO;
import per.stu.weblog.admin.model.vo.tag.FindTagReqVO;
import per.stu.weblog.admin.service.AdminTagService;
import per.stu.weblog.common.aspect.ApiOperationLog;
import per.stu.weblog.common.utils.PageResponse;
import per.stu.weblog.common.utils.Response;

@RestController
@RequestMapping("/interface/admin/tag")
@Api(tags = "Admin 标签模块")
public class AdminTagController {

    @Autowired
    private AdminTagService tagService;

    @PostMapping("/add")
    @ApiOperation(value = "添加标签")
    @ApiOperationLog(description = "添加标签")
    public Response addTag(@RequestBody @Validated AddTagReqVO addTagReqVO) {
        return tagService.addTagList(addTagReqVO);
    }

    @PostMapping("/list")
    @ApiOperation(value = "标签分页数据获取")
    @ApiOperationLog(description = "标签分页数据获取")
    public PageResponse findTagList(@RequestBody @Validated FindTagPageListReqVO findTagPageListReqVO) {
        return tagService.findTagList(findTagPageListReqVO);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除标签")
    @ApiOperationLog(description = "删除分类")
    public Response deleteTag(@RequestBody @Validated DeleteTagReqVO deleteTagReqVO) {
        return tagService.deleteTag(deleteTagReqVO);
    }

    @PostMapping("/search")
    @ApiOperation(value = "标签搜索")
    @ApiOperationLog(description = "标签搜索")
    public Response searchTagList(@RequestBody @Validated FindTagReqVO findTagReqVO) {
        return tagService.searchTagList(findTagReqVO);
    }

}
