package per.stu.weblog.admin.service;

import per.stu.weblog.admin.model.vo.category.AddCategoryReqVO;
import per.stu.weblog.common.utils.Response;

public interface AdminCategoryService {

    /**
     * 添加分类
     * @param addCategoryReqVO
     * @return
     */
    Response addCategory(AddCategoryReqVO addCategoryReqVO);
}
