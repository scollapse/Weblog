package per.stu.weblog.admin.service;

import per.stu.weblog.admin.controller.UpdateArticleReqVO;
import per.stu.weblog.admin.model.vo.article.DeleteArticleReqVO;
import per.stu.weblog.admin.model.vo.article.FindArticleDetailReqVO;
import per.stu.weblog.admin.model.vo.article.FindArticlePageListReqVO;
import per.stu.weblog.admin.model.vo.article.PublishArticleReqVO;
import per.stu.weblog.common.utils.Response;

public interface AdminArticleService {

    /**
     * 发布文章
     * @param publishArticleReqVO
     * @return
     */
    Response publishArticle(PublishArticleReqVO publishArticleReqVO);


    /**
     * 删除文章
     * @param deleteArticleReqVO
     * @return
     */
    Response deleteArticle(DeleteArticleReqVO deleteArticleReqVO);

    /**
     * 查询文章分页数据
     * @param findArticlePageListReqVO
     * @return
     */
    Response findArticlePageList(FindArticlePageListReqVO findArticlePageListReqVO);


    /**
     * 查询文章详情
     * @param findArticleDetailReqVO
     * @return
     */
    Response findArticleDetail(FindArticleDetailReqVO findArticleDetailReqVO);

    /**
     * 更新文章
     * @param updateArticleReqVO
     * @return
     */
    Response updateArticle(UpdateArticleReqVO updateArticleReqVO);
}
