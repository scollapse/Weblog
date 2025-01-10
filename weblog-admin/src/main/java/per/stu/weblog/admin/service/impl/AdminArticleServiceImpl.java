package per.stu.weblog.admin.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import per.stu.weblog.admin.controller.UpdateArticleReqVO;
import per.stu.weblog.admin.convert.ArticleDetailConvert;
import per.stu.weblog.admin.model.vo.article.*;
import per.stu.weblog.admin.service.AdminArticleService;
import per.stu.weblog.common.domain.dos.*;
import per.stu.weblog.common.domain.mapper.*;
import per.stu.weblog.common.enums.ResponseCodeEnum;
import per.stu.weblog.common.excption.BizException;
import per.stu.weblog.common.utils.PageResponse;
import per.stu.weblog.common.utils.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description: 文章管理业务实现类
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
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleTagRelMapper articleTagRelMapper;

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
        // 省略...
        insertTags(articleId, publishTags);

        return Response.success();
    }

    /**
     * 保存标签
     *
     * @param articleId
     * @param publishTags
     */
    private void insertTags(Long articleId, List<String> publishTags) {
        // 筛选提交的标签（表中不存在的标签）
        List<String> notExistTags = null;
        // 筛选提交的标签（表中已存在的标签）
        List<String> existedTags = null;

        // 查询出所有标签
        List<TagDO> tagDOS = tagMapper.selectList(null);
        if (CollectionUtils.isEmpty(tagDOS)) {
            notExistTags = publishTags;
        } else {
            List<String> tagIds = tagDOS.stream().map(tagDO -> String.valueOf(tagDO.getId())).collect(Collectors.toList());
            // 通过 ID 筛选 ，包含对应的 id 就是存在的，否则不存在
            notExistTags = publishTags.stream().filter(tag -> !tagIds.contains(tag)).collect(Collectors.toList());
            existedTags = publishTags.stream().filter(tag -> tagIds.contains(tag)).collect(Collectors.toList());
            // 补充逻辑
            // 还有一种可能：按字符串名称提交上来的标签，也有可能是表中已存在的，比如表中已经有了 Java 标签，用户提交了个 java 小写的标签，需要内部装换为 Java 标签
            Map<String, Long> tagNameIdMap = tagDOS.stream().collect(Collectors.toMap(tagDO -> tagDO.getName().toLowerCase(), TagDO::getId));
            // 使用迭代器进行删除
            Iterator<String> iterator = notExistTags.iterator();
            while (iterator.hasNext()) {
                String tagName = iterator.next();
                // 先尝试转换为小写再查找
                if (tagNameIdMap.containsKey(tagName.toLowerCase())) {
                    // 从不存在的标签中删除
                    iterator.remove();
                    // 加入已存在的标签中
                    existedTags.add(String.valueOf(tagNameIdMap.get(tagName.toLowerCase())));
                }
            }
        }
        // 将提交的标签，存在于表中的标签，插入到文章标签关系表中
        if (!CollectionUtils.isEmpty(existedTags)) {
            List<ArticleTagRelDO> articleTagRelDOS = Lists.newArrayList();
            existedTags.forEach(tagID -> {
                ArticleTagRelDO articleTagRelDO = ArticleTagRelDO.builder()
                        .articleId(articleId)
                        .tagId(Long.parseLong(tagID))
                        .build();
                articleTagRelDOS.add(articleTagRelDO);
            });
            // 批量插入
            articleTagRelMapper.insertBatchSomeColumn(articleTagRelDOS);
        }
        // 保存不存在的标签到数据库
        if (!CollectionUtils.isEmpty(notExistTags)) {
            // 把标签保存到数据库 再插入到文章标签关系表中
            List<ArticleTagRelDO> articleTagRelDOS = Lists.newArrayList();
            notExistTags.forEach(tagName -> {
                TagDO tagDO = TagDO.builder()
                        .name(tagName)
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build();
                tagMapper.insert(tagDO);
                // 拿到插入记录的主键 ID
                Long tagId = tagDO.getId();
                ArticleTagRelDO articleTagRelDO = ArticleTagRelDO.builder()
                        .articleId(articleId)
                        .tagId(tagId)
                        .build();
                articleTagRelDOS.add(articleTagRelDO);
            });
            // 批量插入
            articleTagRelMapper.insertBatchSomeColumn(articleTagRelDOS);
        }
    }


    /**
     * 删除文章
     *
     * @param deleteArticleReqVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response deleteArticle(DeleteArticleReqVO deleteArticleReqVO) {
        Long articleId = deleteArticleReqVO.getId();

        // 1. 删除文章
        articleMapper.deleteById(articleId);

        // 2. 删除文章内容
        articleContentMapper.deleteByArticleId(articleId);

        // 3. 删除文章-分类关联记录
        articleCategoryRelMapper.deleteByArticleId(articleId);

        // 4. 删除文章-标签关联记录
        articleTagRelMapper.deleteByArticleId(articleId);

        return Response.success();
    }

    /**
     * 查询文章分页数据
     *
     * @param findArticlePageListReqVO
     * @return
     */
    @Override
    public Response findArticlePageList(FindArticlePageListReqVO findArticlePageListReqVO) {
        // 获取当前页、以及每页需要展示的数据数量
        Long current = findArticlePageListReqVO.getCurrent();
        Long size = findArticlePageListReqVO.getSize();
        String title = findArticlePageListReqVO.getTitle();
        LocalDate startDate = findArticlePageListReqVO.getStartDate();
        LocalDate endDate = findArticlePageListReqVO.getEndDate();

        // 执行分页查询
        Page<ArticleDO> articleDOPage = articleMapper.selectPageList(current, size, title, startDate, endDate);

        List<ArticleDO> articleDOS = articleDOPage.getRecords();

        // DO 转 VO
        List<FindArticlePageListRspVO> vos = null;
        if (!CollectionUtils.isEmpty(articleDOS)) {
            vos = articleDOS.stream()
                    .map(articleDO -> FindArticlePageListRspVO.builder()
                            .id(articleDO.getId())
                            .title(articleDO.getTitle())
                            .cover(articleDO.getCover())
                            .createTime(articleDO.getCreateTime())
                            .build())
                    .collect(Collectors.toList());
        }

        return PageResponse.success(articleDOPage, vos);
    }

    /**
     * 查询文章详情
     *
     * @param findArticleDetailReqVO
     * @return
     */
    @Override
    public Response findArticleDetail(FindArticleDetailReqVO findArticleDetailReqVO) {
        Long articleId = findArticleDetailReqVO.getId();

        // 查询文章
        ArticleDO articleDO = articleMapper.selectById(articleId);

        if (Objects.isNull(articleDO)) {
            log.warn("==> 查询的文章不存在，articleId: {}", articleId);
            throw new BizException(ResponseCodeEnum.ARTICLE_NOT_FOUND);
        }

        // 查询文章内容
        ArticleContentDO articleContentDO = articleContentMapper.selectByArticleId(articleId);

        // 所属分类
        ArticleCategoryRelDO articleCategoryRelDO = articleCategoryRelMapper.selectByArticleId(articleId);

        // 对应标签
        List<ArticleTagRelDO> articleTagRelDOS = articleTagRelMapper.selectByArticleId(articleId);

        // 获取对应标签 ID 集合
        List<Long> tagIds = articleTagRelDOS.stream().map(ArticleTagRelDO::getTagId).collect(Collectors.toList());

        // DO 转 VO
        FindArticleDetailRspVO vo = ArticleDetailConvert.INSTANCE.convertDO2VO(articleDO);
        vo.setContent(articleContentDO.getContent());
        vo.setCategoryId(articleCategoryRelDO.getCategoryId());
        vo.setTagIds(tagIds);

        return Response.success(vo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response updateArticle(UpdateArticleReqVO updateArticleReqVO) {
        Long articleId = updateArticleReqVO.getId();

        // 1. VO 转 ArticleDO, 并更新
        ArticleDO articleDO = ArticleDO.builder()
                .id(articleId)
                .title(updateArticleReqVO.getTitle())
                .cover(updateArticleReqVO.getCover())
                .summary(updateArticleReqVO.getSummary())
                .updateTime(LocalDateTime.now())
                .build();
        int count = articleMapper.updateById(articleDO);

        // 根据更新是否成功，来判断该文章是否存在
        if (count == 0) {
            log.warn("==> 该文章不存在, articleId: {}", articleId);
            throw new BizException(ResponseCodeEnum.ARTICLE_NOT_FOUND);
        }

        // 2. VO 转 ArticleContentDO，并更新
        ArticleContentDO articleContentDO = ArticleContentDO.builder()
                .articleId(articleId)
                .content(updateArticleReqVO.getContent())
                .build();
        articleContentMapper.updateByArticleId(articleContentDO);


        // 3. 更新文章分类
        Long categoryId = updateArticleReqVO.getCategoryId();

        // 3.1 校验提交的分类是否真实存在
        CategoryDO categoryDO = categoryMapper.selectById(categoryId);
        if (Objects.isNull(categoryDO)) {
            log.warn("==> 分类不存在, categoryId: {}", categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_NOT_EXISTED);
        }

        // 先删除该文章关联的分类记录，再插入新的关联关系
        articleCategoryRelMapper.deleteByArticleId(articleId);
        ArticleCategoryRelDO articleCategoryRelDO = ArticleCategoryRelDO.builder()
                .articleId(articleId)
                .categoryId(categoryId)
                .build();
        articleCategoryRelMapper.insert(articleCategoryRelDO);

        // 4. 保存文章关联的标签集合
        // 先删除该文章对应的标签
        articleTagRelMapper.deleteByArticleId(articleId);
        List<String> publishTags = updateArticleReqVO.getTags();
        insertTags(articleId, publishTags);

        return Response.success();
    }




}

