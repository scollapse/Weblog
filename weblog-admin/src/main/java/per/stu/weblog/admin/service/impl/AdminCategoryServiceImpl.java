package per.stu.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import per.stu.weblog.admin.model.vo.category.AddCategoryReqVO;
import per.stu.weblog.admin.model.vo.category.DeleteCategoryReqVO;
import per.stu.weblog.admin.model.vo.category.FindCategoryPageListReqVO;
import per.stu.weblog.admin.model.vo.category.FindCategoryPageListResVO;
import per.stu.weblog.admin.service.AdminCategoryService;
import per.stu.weblog.common.domain.dos.CategoryDO;
import per.stu.weblog.common.domain.mapper.CategoryMapper;
import per.stu.weblog.common.enums.ResponseCodeEnum;
import per.stu.weblog.common.excption.BizException;
import per.stu.weblog.common.utils.PageResponse;
import per.stu.weblog.common.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    /**
     * 分页查询分类列表
     * @param findCategoryPageListReqVO
     * @return
     */
    @Override
    public PageResponse findCategoryList(FindCategoryPageListReqVO findCategoryPageListReqVO) {
        // 获取分页参数
        Long  pageNum = findCategoryPageListReqVO.getCurrent();
        Long  pageSize = findCategoryPageListReqVO.getSize();
        // 分页对象(查询第几页、每页多少数据)
        Page<CategoryDO> page = new Page<>(pageNum, pageSize);

        // 查询条件
        LambdaQueryWrapper<CategoryDO> queryWrapper = new LambdaQueryWrapper<>();
        String name = findCategoryPageListReqVO.getName();
        LocalDate startDate = findCategoryPageListReqVO.getStartDate();
        LocalDate endDate = findCategoryPageListReqVO.getEndDate();
        queryWrapper
                .like(StringUtils.isNotBlank(name), CategoryDO::getName, name.trim())
                .ge(Objects.nonNull(startDate), CategoryDO::getCreateTime, startDate)
                .le(Objects.nonNull(endDate), CategoryDO::getCreateTime, endDate)
                .orderByDesc(CategoryDO::getCreateTime);

        // 执行分页查询
        Page<CategoryDO> categoryDOPage = categoryMapper.selectPage(page, queryWrapper);
        List<CategoryDO> records = categoryDOPage.getRecords();

        //DO转VO
        List<FindCategoryPageListResVO> vos = null;
        if (CollectionUtils.isNotEmpty(records)){
            vos = records.stream().map(record ->
                FindCategoryPageListResVO.builder()
               .id(record.getId())
               .name(record.getName())
               .createTime(record.getCreateTime())
               .build()
            ).collect(Collectors.toList());
        }
        return PageResponse.success(categoryDOPage,vos);
    }


    /**
     * 删除分类
     * @param deleteCategoryReqVO
     * @return
     */
    @Override
    public Response deleteCategory(DeleteCategoryReqVO deleteCategoryReqVO) {
        // 分类 ID
        Long categoryId = deleteCategoryReqVO.getId();

        // 删除分类
        categoryMapper.deleteById(categoryId);

        return Response.success();
    }
}
