package per.stu.weblog.admin.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import per.stu.weblog.admin.model.vo.category.AddCategoryReqVO;
import per.stu.weblog.admin.service.AdminCategoryService;
import per.stu.weblog.common.domain.dos.CategoryDO;
import per.stu.weblog.common.domain.mapper.CategoryMapper;
import per.stu.weblog.common.enums.ResponseCodeEnum;
import per.stu.weblog.common.excption.BizException;
import per.stu.weblog.common.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Slf4j
public class AdminCategoryServiceImpl implements AdminCategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 添加分类
     * @param addCategoryReqVO
     * @return
     */
    @Override
    public Response addCategory(AddCategoryReqVO addCategoryReqVO) {
        String categoryName = addCategoryReqVO.getName();
        // 判断是否存在同名分类
        CategoryDO categoryDO = categoryMapper.selectByByName(categoryName);
        if (Objects.nonNull(categoryDO)){
            log.warn("分类名称已存在：{}", categoryName);
            throw new BizException(ResponseCodeEnum.CATEGORY_NAME_EXIST);
        }

        // 构建DO对象
        CategoryDO insertCategoryDO = CategoryDO.builder()
                .name(categoryName.trim())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .isDeleted(false)
               .build();
        // 插入数据库
        int result = categoryMapper.insert(insertCategoryDO);
        if (result == 0){
            log.error("添加分类失败：{}", categoryName);
            throw new BizException(ResponseCodeEnum.CATEGORY_ADD_ERROR);
        }
        return Response.success();
    }
}
