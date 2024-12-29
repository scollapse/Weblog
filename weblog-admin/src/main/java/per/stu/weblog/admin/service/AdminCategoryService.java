package per.stu.weblog.admin.service;

import per.stu.weblog.admin.model.vo.category.AddCategoryReqVO;
import per.stu.weblog.admin.model.vo.category.DeleteCategoryReqVO;
import per.stu.weblog.admin.model.vo.category.FindCategoryPageListReqVO;
import per.stu.weblog.common.utils.PageResponse;
import per.stu.weblog.common.utils.Response;

public interface AdminCategoryService {

    /**
     * 添加分类
     * @param addCategoryReqVO
     * @return
     */
    Response addCategory(AddCategoryReqVO addCategoryReqVO);

    /**
     * 分类分页数据查询
     * @param findCategoryPageListReqVO
     * @return
     */
    PageResponse findCategoryList(FindCategoryPageListReqVO findCategoryPageListReqVO);


    /**
     * 删除分类
     * @param deleteCategoryReqVO
     * @return
     */
    Response deleteCategory(DeleteCategoryReqVO deleteCategoryReqVO);
}
