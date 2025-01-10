package per.stu.weblog.admin.service;

import per.stu.weblog.admin.model.vo.article.PublishArticleReqVO;
import per.stu.weblog.common.utils.Response;

public interface AdminArticleService {

    /**
     * 发布文章
     * @param publishArticleReqVO
     * @return
     */
    Response publishArticle(PublishArticleReqVO publishArticleReqVO);
}
