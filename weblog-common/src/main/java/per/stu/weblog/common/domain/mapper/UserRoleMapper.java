package per.stu.weblog.common.domain.mapper;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import per.stu.weblog.common.domain.dos.UserRoleDO;

import java.util.List;

/**
 * @description: 用户角色关系表 Mapper
 * @author: syl
 * @create: 2024-12-24 14:42
 **/
public interface UserRoleMapper extends BaseMapper<UserRoleDO> {
    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    default List<UserRoleDO> selectByUsername(String username) {
        LambdaQueryWrapper<UserRoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRoleDO::getUsername, username);
        return selectList(wrapper);
    }
}
