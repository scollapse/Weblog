package per.stu.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import per.stu.weblog.common.domain.dos.UserDO;

import java.time.LocalDateTime;

/**
 * @author syl
 * @date 2021/07/16 16:59
 */
public interface UserMapper extends BaseMapper<UserDO> {

    default UserDO findByUsername(String username) {
            LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserDO::getUsername, username);
            return selectOne(wrapper);
    }

    default int updatePassword(String username, String password) {
        LambdaUpdateWrapper<UserDO> wrapper = new LambdaUpdateWrapper<>();
        // 设置更新字段
        wrapper.set(UserDO::getPassword, password);
        wrapper.set(UserDO::getUpdateTime, LocalDateTime.now());
        // 设置条件
        wrapper.eq(UserDO::getUsername, username);
        return update(null,wrapper);
    }

}
