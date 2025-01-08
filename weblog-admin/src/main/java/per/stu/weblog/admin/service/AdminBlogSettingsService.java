package per.stu.weblog.admin.service;


import per.stu.weblog.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;
import per.stu.weblog.common.utils.Response;

/**
 * @description:  AdminBlogSettingsService
 * @author: syl
 * @create: 2025-01-08 09:53
 **/
public interface AdminBlogSettingsService {
    /**
     * 更新博客设置信息
     * @param updateBlogSettingsReqVO
     * @return
     */
    Response updateBlogSettings(UpdateBlogSettingsReqVO updateBlogSettingsReqVO);


    /**
     * 获取博客设置详情
     * @return
     */
    Response findDetail();

}
