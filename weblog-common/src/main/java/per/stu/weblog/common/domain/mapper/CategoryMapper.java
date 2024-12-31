package per.stu.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import per.stu.weblog.common.domain.dos.CategoryDO;

import java.time.LocalDate;
import java.util.Objects;

public interface CategoryMapper extends BaseMapper<CategoryDO> {

    /**
     * 根据名称查询分类
     *
     * @param name
     * @return
     */
    default CategoryDO selectByByName(String name) {
        // 构建查询条件
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryDO::getName, name);
        // 查询
        return this.selectOne(wrapper);
    }

  default  Page<CategoryDO> findCategoryList(Long pageNum, Long pageSize, String name, LocalDate startDate, LocalDate endDate){
      // 分页对象(查询第几页、每页多少数据)
      Page<CategoryDO> page = new Page<>(pageNum, pageSize);
      // 查询条件
      LambdaQueryWrapper<CategoryDO> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper
              .like(StringUtils.isNotBlank(name), CategoryDO::getName, name.trim())
              .ge(Objects.nonNull(startDate), CategoryDO::getCreateTime, startDate)
              .le(Objects.nonNull(endDate), CategoryDO::getCreateTime, endDate)
              .orderByDesc(CategoryDO::getCreateTime);

      // 执行分页查询
      return this.selectPage(page, queryWrapper);
  }
}
