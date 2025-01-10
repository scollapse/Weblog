package per.stu.weblog.admin.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import per.stu.weblog.admin.model.vo.article.PublishArticleReqVO;
import per.stu.weblog.admin.service.AdminArticleService;
import per.stu.weblog.common.domain.dos.ArticleCategoryRelDO;
import per.stu.weblog.common.domain.dos.ArticleContentDO;
import per.stu.weblog.common.domain.dos.ArticleDO;
import per.stu.weblog.common.domain.dos.CategoryDO;
import per.stu.weblog.common.domain.mapper.ArticleCategoryRelMapper;
import per.stu.weblog.common.domain.mapper.ArticleContentMapper;
import per.stu.weblog.common.domain.mapper.ArticleMapper;
import per.stu.weblog.common.domain.mapper.CategoryMapper;
import per.stu.weblog.common.enums.ResponseCodeEnum;
import per.stu.weblog.common.excption.BizException;
import per.stu.weblog.common.utils.Response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @description:  文章管理业务实现类
 * @author: syl
 * @create: 2025-01-10 14:26
 **/
@Service
@Slf4j
public class AdminArticleServiceImpl implements AdminArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleContentMapper articleContentMapper;
    @Autowired
    private ArticleCategoryRelMapper articleCategoryRelMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 发布文章
     *
     * @param publishArticleReqVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response publishArticle(PublishArticleReqVO publishArticleReqVO) {

        // 3. 处理文章关联的分类
        Long categoryId = publishArticleReqVO.getCategoryId();

        // 3.1 校验提交的分类是否真实存在
        CategoryDO categoryDO = categoryMapper.selectById(categoryId);
        if (Objects.isNull(categoryDO)) {
            log.warn("==> 分类不存在, categoryId: {}", categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_NOT_EXISTED);
        }

        // 1. VO 转 ArticleDO, 并保存
        ArticleDO articleDO = ArticleDO.builder()
                .title(publishArticleReqVO.getTitle())
                .cover(publishArticleReqVO.getCover())
                .summary(publishArticleReqVO.getSummary())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        articleMapper.insert(articleDO);

        // 拿到插入记录的主键 ID
        Long articleId = articleDO.getId();

        // 2. VO 转 ArticleContentDO，并保存
        ArticleContentDO articleContentDO = ArticleContentDO.builder()
                .articleId(articleId)
                .content(publishArticleReqVO.getContent())
                .build();
        articleContentMapper.insert(articleContentDO);



        ArticleCategoryRelDO articleCategoryRelDO = ArticleCategoryRelDO.builder()
                .articleId(articleId)
                .categoryId(categoryId)
                .build();
        articleCategoryRelMapper.insert(articleCategoryRelDO);

        // 4. 保存文章关联的标签集合
        List<String> publishTags = publishArticleReqVO.getTags();
        insertTags(publishTags);

        return Response.success();
    }

    /**
     * 保存标签
     * @param publishTags
     */
    private void insertTags(List<String> publishTags) {
        // TODO
    }
}

