package per.stu.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import per.stu.weblog.common.config.InsertBatchMapper;
import per.stu.weblog.common.domain.dos.ArticleTagRelDO;

public interface ArticleTagRelMapper extends InsertBatchMapper<ArticleTagRelDO> {
    /**
     * 根据文章 ID 删除关联记录
     * @param articleId
     * @return
     */
    default int deleteByArticleId(Long articleId) {
        return delete(Wrappers.<ArticleTagRelDO>lambdaQuery()
                .eq(ArticleTagRelDO::getArticleId, articleId));
    }
}

