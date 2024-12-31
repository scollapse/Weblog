package per.stu.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import per.stu.weblog.common.domain.dos.TagDO;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public interface TagMapper extends BaseMapper<TagDO> {

    default  Page<TagDO> findTagList(Long pageNum, Long pageSize,String name, LocalDate startDate, LocalDate endDate) {
        // 分页对象(查询第几页、每页多少数据)
        Page<TagDO> page = new Page<>(pageNum, pageSize);
        // 查询条件
        LambdaQueryWrapper<TagDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StringUtils.isNotBlank(name), TagDO::getName, name.trim())
                .ge(Objects.nonNull(startDate), TagDO::getCreateTime, startDate)
                .le(Objects.nonNull(endDate), TagDO::getCreateTime, endDate)
                .orderByDesc(TagDO::getCreateTime);

        // 执行分页查询
        return selectPage(page, queryWrapper);
    }

   default List<TagDO> searchTagList(String key){
        LambdaQueryWrapper<TagDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(TagDO::getName, key.trim());
        return selectList(queryWrapper);
   }
}
