package per.stu.weblog.admin.service;

import per.stu.weblog.admin.model.vo.tag.AddTagReqVO;
import per.stu.weblog.admin.model.vo.tag.DeleteTagReqVO;
import per.stu.weblog.admin.model.vo.tag.FindTagPageListReqVO;
import per.stu.weblog.admin.model.vo.tag.FindTagReqVO;
import per.stu.weblog.common.utils.PageResponse;
import per.stu.weblog.common.utils.Response;

public interface AdminTagService {

    Response addTagList(AddTagReqVO addTagReqVO);

    PageResponse findTagList(FindTagPageListReqVO findTagPageListReqVO);

    Response deleteTag(DeleteTagReqVO deleteTagReqVO);

    Response searchTagList(FindTagReqVO findTagReqVO);
}
