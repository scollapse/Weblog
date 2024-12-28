package per.stu.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import per.stu.weblog.common.domain.dos.CategoryDO;

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
}
